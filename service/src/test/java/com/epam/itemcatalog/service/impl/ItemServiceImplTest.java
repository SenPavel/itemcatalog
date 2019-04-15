package com.epam.itemcatalog.service.impl;

import com.epam.itemcatalog.model.dto.CurrencyDto;
import com.epam.itemcatalog.model.dto.ItemDto;
import com.epam.itemcatalog.model.entity.Item;
import com.epam.itemcatalog.repository.EntityRepository;
import com.epam.itemcatalog.repository.specification.CriteriaSpecification;
import com.epam.itemcatalog.service.config.ServiceConfiguration;
import com.epam.itemcatalog.service.converter.impl.CurrencyConverterImpl;
import com.epam.itemcatalog.service.exception.AlreadyExistsException;
import com.epam.itemcatalog.service.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
@ContextConfiguration(classes = {ServiceConfiguration.class})
public class ItemServiceImplTest {

    @Mock
    private EntityRepository<Item> repository;

    private Item testItem = new Item();

    private ItemDto testItemDto = new ItemDto();

    private List<Item> testItemsList;

    @Spy
    private CurrencyConverterImpl converter;

    @InjectMocks
    private ItemServiceImpl service;

    @Before
    public void initMock() {
        MockitoAnnotations.initMocks(this);

        testItem.setId(1L);
        testItem.setName("name");
        testItem.setPrice(new BigDecimal(1.0));

        testItemDto.setId(testItem.getId());
        testItemDto.setName(testItem.getName());
        testItemDto.setPrice(testItem.getPrice());

        testItemsList = new ArrayList<>(Arrays.asList(new Item(), new Item()));
    }

    @Test(expected = AlreadyExistsException.class)
    public void shouldThrowExceptionWhenAddItem() throws AlreadyExistsException {
        when(repository.findSingleBy(any(CriteriaSpecification.class))).thenReturn(Optional.of(testItem));

        service.save(testItemDto);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowExceptionWhenUpdateItemWithWrongId() throws NotFoundException, AlreadyExistsException {
        when(repository.findSingleBy(any(CriteriaSpecification.class))).thenReturn(Optional.empty());

        service.update(1L, testItemDto);
    }

    @Test(expected = AlreadyExistsException.class)
    public void shouldThrowExceptionWhenUpdateItemWithAlreadyExistsName() throws NotFoundException, AlreadyExistsException {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(2L);
        itemDto.setName(testItem.getName());

        when(repository.findSingleBy(any(CriteriaSpecification.class))).thenReturn(Optional.of(testItem));

        service.update(2L, itemDto);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowExceptionWhenRemoveItem() throws NotFoundException {
        when(repository.findSingleBy(any(CriteriaSpecification.class))).thenReturn(Optional.empty());

        service.remove(1L);
    }

    @Test
    public void shouldFindById() throws NotFoundException {
        when(repository.findSingleBy(any(CriteriaSpecification.class))).thenReturn(Optional.of(testItem));

        ItemDto actualItemDto = service.findById(1L);

        Assert.assertEquals(testItemDto, actualItemDto);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowExceptionWhenFindById() throws NotFoundException {
        when(repository.findSingleBy(any(CriteriaSpecification.class))).thenReturn(Optional.empty());

        service.findById(1L);
    }

    @Test
    public void shouldFindByName() throws NotFoundException {
        when(repository.findSingleBy(any(CriteriaSpecification.class))).thenReturn(Optional.of(testItem));

        ItemDto actualItemDto = service.findByName("name");

        Assert.assertEquals(testItemDto, actualItemDto);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowExceptionWhenFindNyName() throws NotFoundException {
        when(repository.findSingleBy(any(CriteriaSpecification.class))).thenReturn(Optional.empty());

        service.findByName("name");
    }

    @Test
    public void shouldFindAll() {
        when(repository.findBy(any(CriteriaSpecification.class))).thenReturn(testItemsList);

        List<ItemDto> actualItemDtoList = service.findAll();

        Assert.assertEquals(testItemsList.size(), actualItemDtoList.size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowExceptionWhenFindAllWithSortingWithWrongSortBy() {
        when(repository.findBy(any(CriteriaSpecification.class))).thenReturn(testItemsList);

        service.findAllWithSorting("sort", "asc");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrowExceptionWhenFindAllWithSortingWithWrongSortOrder() {
        when(repository.findBy(any(CriteriaSpecification.class))).thenReturn(testItemsList);

        service.findAllWithSorting("name", "order");
    }

    @Test
    public void shouldConvertItemPrice() {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setRate(new BigDecimal(0.5));

        ItemDto actualItemDto = service.convertItemPrice(testItemDto, currencyDto);

        Assert.assertEquals(0.5, actualItemDto.getPrice().doubleValue(), 0);
    }
}

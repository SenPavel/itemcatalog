package com.epam.itemcatalog.service.impl;

import com.epam.itemcatalog.model.dto.CurrencyDto;
import com.epam.itemcatalog.model.entity.Currency;
import com.epam.itemcatalog.repository.EntityRepository;
import com.epam.itemcatalog.repository.specification.CriteriaSpecification;
import com.epam.itemcatalog.service.config.ServiceConfiguration;
import com.epam.itemcatalog.service.exception.NotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
public class CurrencyServiceImplTest {

    @Mock
    EntityRepository<Currency> repository;

    private Currency testCurrency = new Currency();

    private CurrencyDto testCurrencyDto = new CurrencyDto();

    @InjectMocks
    private CurrencyServiceImpl service;

    @Before
    public void initMock() {
        MockitoAnnotations.initMocks(this);

        testCurrency.setId(1L);
        testCurrency.setCode("AAA");
        testCurrency.setRate(new BigDecimal(1.0));

        testCurrencyDto.setId(testCurrency.getId());
        testCurrencyDto.setCode(testCurrency.getCode());
        testCurrencyDto.setRate(testCurrency.getRate());
    }

    @Test
    public void shouldFindByCode() throws NotFoundException {
        when(repository.findSingleBy(any(CriteriaSpecification.class))).thenReturn(Optional.of(testCurrency));

        CurrencyDto actualCurrencyDto = service.findByCode("AAA");

        Assert.assertEquals(testCurrencyDto, actualCurrencyDto);
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowExceptionWhenFindByCode() throws NotFoundException {
        when(repository.findSingleBy(any(CriteriaSpecification.class))).thenReturn(Optional.empty());

        service.findByCode("AAA");
    }

}

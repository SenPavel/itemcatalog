package com.epam.itemcatalog.service.impl;

import com.epam.itemcatalog.model.dto.CurrencyDto;
import com.epam.itemcatalog.model.dto.ItemDto;
import com.epam.itemcatalog.model.entity.Item;
import com.epam.itemcatalog.repository.EntityRepository;
import com.epam.itemcatalog.repository.helper.SortDirection;
import com.epam.itemcatalog.repository.specification.CriteriaSpecification;
import com.epam.itemcatalog.repository.specification.impl.item.FindAllItemCriteriaSpecification;
import com.epam.itemcatalog.repository.specification.impl.item.FindAllWithSortingSpecification;
import com.epam.itemcatalog.repository.specification.impl.item.FindItemByIdSpecification;
import com.epam.itemcatalog.repository.specification.impl.item.FindItemByNameCriteriaSpecification;
import com.epam.itemcatalog.service.ItemService;
import com.epam.itemcatalog.service.converter.CurrencyConverter;
import com.epam.itemcatalog.service.exception.AlreadyExistsException;
import com.epam.itemcatalog.service.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

/**
 * {@inheritDoc}
 */
@Service
public class ItemServiceImpl implements ItemService {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String ITEM_NOT_FOUND_ERROR = "Item not found";
    private static final String ITEM_ALREADY_EXISTS_ERROR = "Item with that name already exists";
    private static final String SORT_VALUE_NOT_SUPPORTED_ERROR = "This value for sort isn't supported";

    private ModelMapper modelMapper = new ModelMapper();
    private EntityRepository<Item> repository;
    private CurrencyConverter converter;

    @Autowired
    public ItemServiceImpl(EntityRepository<Item> repository, CurrencyConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public ItemDto save(ItemDto itemDto) throws AlreadyExistsException {
        CriteriaSpecification<Item> specification = new FindItemByNameCriteriaSpecification(itemDto.getName());
        Optional<Item> optionalItem = repository.findSingleBy(specification);

        if (optionalItem.isPresent()) {
            throw new AlreadyExistsException(ITEM_ALREADY_EXISTS_ERROR);
        } else {
            Item item = repository.save(dtoToEntity(itemDto));
            return entityToDto(item);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void update(Long id, ItemDto itemDto) throws NotFoundException, AlreadyExistsException {
        CriteriaSpecification<Item> specification = new FindItemByIdSpecification(id);
        Optional<Item> optionalItem = repository.findSingleBy(specification);

        if (optionalItem.isPresent()) {
            CriteriaSpecification<Item> criteriaSpecification = new FindItemByNameCriteriaSpecification(itemDto.getName());
            Optional<Item> itemOptional = repository.findSingleBy(criteriaSpecification);
            if (itemOptional.isPresent() && !itemOptional.get().getId().equals(id)) {
                throw new AlreadyExistsException(ITEM_ALREADY_EXISTS_ERROR);
            } else {
                itemDto.setId(id);
                repository.save(dtoToEntity(itemDto));
            }
        } else {
            throw new NotFoundException(ITEM_NOT_FOUND_ERROR);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void remove(Long id) throws NotFoundException {
        CriteriaSpecification<Item> specification = new FindItemByIdSpecification(id);
        Optional<Item> optionalItem = repository.findSingleBy(specification);

        if (optionalItem.isPresent()) {
            repository.remove(optionalItem.get().getId());
        } else {
            throw new NotFoundException(ITEM_NOT_FOUND_ERROR);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public ItemDto findById(Long id) throws NotFoundException {
        CriteriaSpecification<Item> specification = new FindItemByIdSpecification(id);
        Optional<Item> optionalItem = repository.findSingleBy(specification);

        if (optionalItem.isPresent()) {
            return entityToDto(optionalItem.get());
        } else {
            throw new NotFoundException(ITEM_NOT_FOUND_ERROR);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public ItemDto findByName(String name) throws NotFoundException {
        CriteriaSpecification<Item> specification = new FindItemByNameCriteriaSpecification(name);
        Optional<Item> optionalItem = repository.findSingleBy(specification);

        if (optionalItem.isPresent()) {
            return entityToDto(optionalItem.get());
        } else {
            throw new NotFoundException(ITEM_NOT_FOUND_ERROR);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<ItemDto> findAll() {
        CriteriaSpecification<Item> specification = new FindAllItemCriteriaSpecification();
        List<Item> items = repository.findBy(specification);
        return entityListToDtoList(items);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<ItemDto> findAllWithSorting(String sortBy, String sortOrder) {
        if (isEqual(sortBy, ID, NAME, PRICE) && isEqual(sortOrder, SortDirection.getSortDirections())) {
            CriteriaSpecification<Item> specification = new FindAllWithSortingSpecification(sortBy, sortOrder);
            List<Item> items = repository.findBy(specification);
            return entityListToDtoList(items);
        } else {
            throw new UnsupportedOperationException(SORT_VALUE_NOT_SUPPORTED_ERROR);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ItemDto> convertItemsPrice(List<ItemDto> itemDtoList, CurrencyDto currencyDto) {
        itemDtoList.forEach(x -> converter.convert(x, currencyDto));
        return itemDtoList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemDto convertItemPrice(ItemDto itemDto, CurrencyDto currencyDto) {
        return converter.convert(itemDto, currencyDto);
    }

    private boolean isEqual(String sortBy, String... params) {
        for (String param : params) {
            if (sortBy.equals(param)) {
                return true;
            }
        }
        return false;
    }

    private ItemDto entityToDto(Item item) {
        return modelMapper.map(item, ItemDto.class);
    }

    private Item dtoToEntity(ItemDto itemDto) {
        return modelMapper.map(itemDto, Item.class);
    }

    private List<Item> dtoListToEntityList(List<ItemDto> itemDtos) {
        Type listType = new TypeToken<List<Item>>() {}.getType();
        return modelMapper.map(itemDtos, listType);
    }

    private List<ItemDto> entityListToDtoList(List<Item> items) {
        Type listType = new TypeToken<List<ItemDto>>() {}.getType();
        return modelMapper.map(items, listType);
    }
}

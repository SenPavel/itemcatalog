package com.epam.itemcatalog.service;

import com.epam.itemcatalog.model.dto.CurrencyDto;
import com.epam.itemcatalog.model.dto.ItemDto;
import com.epam.itemcatalog.service.exception.AlreadyExistsException;
import com.epam.itemcatalog.service.exception.NotFoundException;

import java.util.List;

/**
 * ItemService encapsulate business logic for Item {@link com.epam.itemcatalog.model.item.Item}
 * @see com.epam.itemcatalog.service.impl.ItemServiceImpl
 */
public interface ItemService {

    /**
     * Save item to database
     * @param itemDto
     * @return Saving item
     * @throws AlreadyExistsException when item with given name already exists
     */
    ItemDto save(ItemDto itemDto) throws AlreadyExistsException;

    /**
     * Update item in database
     * @param id item id
     * @param itemDto
     * @throws NotFoundException when item with given id doesn't not exists
     * @throws AlreadyExistsException when item with given name already exists
     */
    void update(Long id, ItemDto itemDto) throws NotFoundException, AlreadyExistsException;

    /**
     * Remove item from database
     * @param id
     * @throws NotFoundException when item with given id not found
     */
    void remove(Long id) throws NotFoundException;

    /**
     * Find item by id
     * @param id
     * @return Found item
     * @throws NotFoundException when item with given id not found
     */
    ItemDto findById(Long id) throws NotFoundException;

    /**
     * Find item by name
     * @param name
     * @return Found item
     * @throws NotFoundException when item with given name not found
     */
    ItemDto findByName(String name) throws NotFoundException;

    /**
     * Find all items from database
     * @return list of items
     */
    List<ItemDto> findAll();

    /**
     * Find all items from database with sorting
     * @param sortBy field name by which to sort
     * @param sortOrder sort order
     * @return
     */
    List<ItemDto> findAllWithSorting(String sortBy, String sortOrder);

    /**
     * Convert items prices with given currency rate
     * @param itemDtoList 
     * @param currencyDto
     * @return items with converted prices
     */
    List<ItemDto> convertItemsPrice(List<ItemDto> itemDtoList, CurrencyDto currencyDto);

    /**
     * Convert item with given currency rate
     * @param itemDto
     * @param currencyDto
     * @return item with converted price
     */
    ItemDto convertItemPrice(ItemDto itemDto, CurrencyDto currencyDto);
}

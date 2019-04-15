package com.epam.itemcatalog.web.controller;

import com.epam.itemcatalog.model.dto.CurrencyDto;
import com.epam.itemcatalog.model.dto.ItemDto;
import com.epam.itemcatalog.service.CurrencyService;
import com.epam.itemcatalog.service.ItemService;
import com.epam.itemcatalog.service.exception.AlreadyExistsException;
import com.epam.itemcatalog.service.exception.NotFoundException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "Item catalog")
@RestController
@RequestMapping(value = "/v1/items")
public class ItemController {

    private ItemService itemService;
    private CurrencyService currencyService;

    @Autowired
    public ItemController(ItemService itemService, CurrencyService currencyService) {
        this.itemService = itemService;
        this.currencyService = currencyService;
    }

    @ApiOperation(value = "Add new item", response = ItemDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful add item"),
            @ApiResponse(code = 400, message = "Item with given name already exists"),
            @ApiResponse(code = 400, message = "Invalid item fields values")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto addItem(@ApiParam(value = "Item object store in database", required = true) @RequestBody @Valid ItemDto itemDto) throws AlreadyExistsException {
        return itemService.save(itemDto);
    }

    @ApiOperation(value = "Update item")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successful update item"),
            @ApiResponse(code = 400, message = "Item with given name already exists"),
            @ApiResponse(code = 400, message = "Item with given id doesn't exists"),
            @ApiResponse(code = 400, message = "Invalid item fields values")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateItem(@ApiParam(value = "Update item object", required = true) @RequestBody @Valid ItemDto itemDto,
                           @ApiParam(value = "Item id to update item object", required = true) @PathVariable("id") Long id) throws NotFoundException, AlreadyExistsException {
        itemService.update(id, itemDto);
    }

    @ApiOperation(value = "Delete item")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successful delete item"),
            @ApiResponse(code = 400, message = "Item with given id doesn't exists")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@ApiParam(value = "Item id to delete item object", required = true) @PathVariable Long id) throws NotFoundException {
        itemService.remove(id);
    }

    @ApiOperation(value = "Get all items with optional sorting and optional converted prices", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful get all items"),
            @ApiResponse(code = 400, message = "Currency with given code not found"),
            @ApiResponse(code = 400, message = "Wrong sortBy or sortOder parameters")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDto> getAll(@ApiParam(value = "Field name by which sort", defaultValue = "id") @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
                                @ApiParam(value = "Sort order", defaultValue = "asc") @RequestParam(value = "sortOrder", required = false, defaultValue = "asc") String order,
                                @ApiParam(value = "Currency code to converter items price") @RequestParam(value = "code", required = false) String code) throws NotFoundException {
        List<ItemDto> itemDtoList = itemService.findAllWithSorting(sortBy, order);
        if (code == null) {
            return itemDtoList;
        }

        CurrencyDto currencyDto = currencyService.findByCode(code);
        return itemService.convertItemsPrice(itemDtoList, currencyDto);
    }

    @ApiOperation(value = "Get item by id", response = ItemDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful find item"),
            @ApiResponse(code = 400, message = "Item with given id not found")
    })
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto getById(@ApiParam(value = "Item id to get item object", required = true) @PathVariable("id") Long id,
                           @ApiParam(value = "Currency code to converter item price") @RequestParam(value = "code", required = false) String code) throws NotFoundException {
        ItemDto itemDto = itemService.findById(id);
        if (code == null) {
            return itemDto;
        }

        CurrencyDto currencyDto = currencyService.findByCode(code);
        return itemService.convertItemPrice(itemDto, currencyDto);
    }

    @ApiOperation(value = "Get item by name", response = ItemDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful find item"),
            @ApiResponse(code = 400, message = "Item with given name not found")
    })
    @GetMapping(params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    public ItemDto getByName(@ApiParam(value = "Item name to get item object", required = true) @RequestParam(value = "name") String name,
                             @ApiParam(value = "Currency code to converter item price") @RequestParam(value = "code", required = false) String code) throws NotFoundException {
        ItemDto itemDto = itemService.findByName(name);
        if (code == null) {
            return itemDto;
        }

        CurrencyDto currencyDto = currencyService.findByCode(code);
        return itemService.convertItemPrice(itemDto, currencyDto);
    }



}

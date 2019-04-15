package com.epam.itemcatalog.service.converter;

import com.epam.itemcatalog.model.dto.CurrencyDto;
import com.epam.itemcatalog.model.dto.ItemDto;

/**
 * CurrencyConverter convert items prices {@link com.epam.itemcatalog.model.entity.Item}
 * with given currency {@link com.epam.itemcatalog.model.entity.Currency}
 * @see com.epam.itemcatalog.service.converter.impl.CurrencyConverterImpl
 */
public interface CurrencyConverter {

    /**
     * Convert item price
     * @param itemDto
     * @param currencyDto
     * @return item with converted price
     */
    ItemDto convert(ItemDto itemDto, CurrencyDto currencyDto);
}

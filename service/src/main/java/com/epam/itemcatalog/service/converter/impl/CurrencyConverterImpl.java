package com.epam.itemcatalog.service.converter.impl;

import com.epam.itemcatalog.model.dto.CurrencyDto;
import com.epam.itemcatalog.model.dto.ItemDto;
import com.epam.itemcatalog.service.converter.CurrencyConverter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * {@inheritDoc}
 */
@Component
public class CurrencyConverterImpl implements CurrencyConverter {

    /**
     * {@inheritDoc}
     */
    @Override
    public ItemDto convert(ItemDto itemDto, CurrencyDto currencyDto) {
        BigDecimal resultPrice = itemDto.getPrice().multiply(currencyDto.getRate());
        itemDto.setPrice(resultPrice);

        return itemDto;
    }
}

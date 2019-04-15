package com.epam.itemcatalog.service;

import com.epam.itemcatalog.model.dto.CurrencyDto;
import com.epam.itemcatalog.service.exception.NotFoundException;

/**
 * ItemService encapsulate business logic for currency {@link com.epam.itemcatalog.model.entity.Currency}
 * @see com.epam.itemcatalog.service.impl.CurrencyServiceImpl
 */
public interface CurrencyService {

    /**
     * Save Currency to database
     * @param currencyDto
     */
    void save(CurrencyDto currencyDto);

    /**
     * Find currency by code
     * @param code
     * @return Found Currency
     * @throws NotFoundException when currency with given code not found
     */
    CurrencyDto findByCode(String code) throws NotFoundException;
}

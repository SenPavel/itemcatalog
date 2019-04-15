package com.epam.itemcatalog.service.impl;

import com.epam.itemcatalog.model.dto.CurrencyDto;
import com.epam.itemcatalog.model.entity.Currency;
import com.epam.itemcatalog.repository.EntityRepository;
import com.epam.itemcatalog.repository.specification.CriteriaSpecification;
import com.epam.itemcatalog.repository.specification.impl.currency.FindCurrencyByCodeSpecification;
import com.epam.itemcatalog.service.CurrencyService;
import com.epam.itemcatalog.service.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * {@inheritDoc}
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {

    private static final String CURRENCY_NOT_FOUND_ERROR = "Currency not found";

    private ModelMapper modelMapper = new ModelMapper();
    private EntityRepository<Currency> repository;

    @Autowired
    public CurrencyServiceImpl(EntityRepository<Currency> repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void save(CurrencyDto currencyDto) {
        CriteriaSpecification<Currency> specification = new FindCurrencyByCodeSpecification(currencyDto.getCode().toUpperCase());
        Optional<Currency> optionalCurrency = repository.findSingleBy(specification);

        optionalCurrency.ifPresent(currency -> currencyDto.setId(currency.getId()));
        repository.save(dtoToEntity(currencyDto));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public CurrencyDto findByCode(String code) throws NotFoundException {
        CriteriaSpecification<Currency> specification = new FindCurrencyByCodeSpecification(code.toUpperCase());
        Optional<Currency> optionalCurrency = repository.findSingleBy(specification);

        if (optionalCurrency.isPresent()) {
            return entityToDto(optionalCurrency.get());
        } else {
            throw new NotFoundException(CURRENCY_NOT_FOUND_ERROR);
        }
    }

    private CurrencyDto entityToDto(Currency currency) {
        return modelMapper.map(currency, CurrencyDto.class);
    }

    private Currency dtoToEntity(CurrencyDto currencyDto) {
        return modelMapper.map(currencyDto, Currency.class);
    }
}

package com.epam.itemcatalog.repository.specification.impl.currency;

import com.epam.itemcatalog.model.entity.Currency;
import com.epam.itemcatalog.repository.specification.CriteriaSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindAllCurrencyCriteriaSpecification implements CriteriaSpecification<Currency> {
    @Override
    public CriteriaQuery<Currency> toCriteriaQuery(CriteriaQuery<Currency> query, CriteriaBuilder builder) {
        Root<Currency> root = query.from(Currency.class);
        return query.select(root);
    }
}

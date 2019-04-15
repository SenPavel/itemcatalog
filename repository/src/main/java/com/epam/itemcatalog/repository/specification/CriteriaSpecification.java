package com.epam.itemcatalog.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * CriteriaSpecification provides opportunity to build select SQL query
 * @param <T>
 */
public interface CriteriaSpecification<T> {

    /**
     * @param query
     * @param builder
     * @return
     */
    CriteriaQuery<T> toCriteriaQuery(CriteriaQuery<T> query, CriteriaBuilder builder);
}

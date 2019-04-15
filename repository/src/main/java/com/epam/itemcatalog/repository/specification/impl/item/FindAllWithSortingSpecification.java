package com.epam.itemcatalog.repository.specification.impl.item;

import com.epam.itemcatalog.model.entity.Item;
import com.epam.itemcatalog.repository.helper.SortDirection;
import com.epam.itemcatalog.repository.specification.CriteriaSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindAllWithSortingSpecification implements CriteriaSpecification<Item> {

    private String orderBy;
    private String order;

    public FindAllWithSortingSpecification(String sortBy, String order) {
        this.orderBy = sortBy;
        this.order = order;
    }

    @Override
    public CriteriaQuery<Item> toCriteriaQuery(CriteriaQuery<Item> query, CriteriaBuilder builder) {
        Root<Item> root = query.from(Item.class);
        if (SortDirection.ASCENDING.getSortDirection().equals(order)) {
            return query.orderBy(builder.asc(root.get(orderBy)));
        } else {
            return query.orderBy(builder.desc(root.get(orderBy)));
        }
    }
}

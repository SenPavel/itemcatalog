package com.epam.itemcatalog.repository.specification.impl.item;

import com.epam.itemcatalog.model.entity.Item;
import com.epam.itemcatalog.model.entity.Item_;
import com.epam.itemcatalog.repository.specification.CriteriaSpecification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class FindItemByNameCriteriaSpecification implements CriteriaSpecification<Item> {

    private String name;

    public FindItemByNameCriteriaSpecification(String name) {
        this.name = name;
    }

    @Override
    public CriteriaQuery<Item> toCriteriaQuery(CriteriaQuery<Item> query, CriteriaBuilder builder) {
        Root<Item> root = query.from(Item.class);
        return query.where(builder.equal(root.get(Item_.name), name));
    }
}

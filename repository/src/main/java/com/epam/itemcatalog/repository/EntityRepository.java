package com.epam.itemcatalog.repository;

import com.epam.itemcatalog.model.entity.BaseEntity;
import com.epam.itemcatalog.repository.specification.CriteriaSpecification;

import java.util.List;
import java.util.Optional;

/**
 * Entity repository provide operation for working with
 * database for all entities which extends from BaseEntity {@link BaseEntity}
 * such as @see save, remove,
 * @param <T>
 * @see com.epam.itemcatalog.repository.impl.EntityRepositoryImpl
 */
public interface EntityRepository<T extends BaseEntity> {

    /**
     * Save or update generic entity {@link T} to database
     * If entity have id - save, otherwise update
     * @param item
     * @return saving entity
     */
    T save(T item);

    /**
     * Remove entity with given id from database
     * @param id
     */
    void remove(Long id);

    /**
     * Find list of entities from database by CriteriaSpecification {@link CriteriaSpecification}
     * @param specification
     * @return list of entities
     */
    List<T> findBy(CriteriaSpecification<T> specification);

    /**
     * Find single object from database by CriteriaSpecification {@link CriteriaSpecification}
     * @param specification
     * @return Optional of entity
     */
    Optional<T> findSingleBy(CriteriaSpecification<T> specification);
}

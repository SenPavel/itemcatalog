package com.epam.itemcatalog.repository.impl;

import com.epam.itemcatalog.model.entity.BaseEntity;
import com.epam.itemcatalog.repository.EntityRepository;
import com.epam.itemcatalog.repository.specification.CriteriaSpecification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

/**
 * {@inheritDoc}
 * @param <T>
 */
@Repository
public class EntityRepositoryImpl<T extends BaseEntity> implements EntityRepository<T> {

    private final Class<T> typeClass;

    @PersistenceContext
    private EntityManager entityManager;

    public EntityRepositoryImpl(Class<T> typeClass) {
        this.typeClass = typeClass;
    }

    /**
     * {@inheritDoc}
     * @param item
     * @return
     */
    @Override
    public T save(T item) {
        return entityManager.merge(item);
    }

    /**
     * {@inheritDoc}
     * @param id
     */
    @Override
    public void remove(Long id) {
        T item = entityManager.find(typeClass, id);
        entityManager.remove(item);
    }

    /**
     * {@inheritDoc}
     * @param specification
     * @return
     */
    @Override
    public List<T> findBy(CriteriaSpecification<T> specification) {
        CriteriaQuery<T> query = buildQuery(specification);
        return entityManager.createQuery(query).getResultList();
    }

    /**
     * {@inheritDoc}
     * @param specification
     * @return
     */
    @Override
    public Optional<T> findSingleBy(CriteriaSpecification<T> specification) {
        List<T> items = findBy(specification);
        return !items.isEmpty() ? Optional.of(items.get(0)) : Optional.empty();
    }

    private CriteriaQuery<T> buildQuery(CriteriaSpecification<T> specification) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(typeClass);
        return specification.toCriteriaQuery(query, builder);
    }
}

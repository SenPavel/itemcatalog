package com.epam.itemcatalog.repository.impl;

import com.epam.itemcatalog.model.entity.Item;
import com.epam.itemcatalog.repository.EntityRepository;
import com.epam.itemcatalog.repository.config.EmbeddedDbConfiguration;
import com.epam.itemcatalog.repository.config.HibernateConfiguration;
import com.epam.itemcatalog.repository.config.RepositoryConfiguration;
import com.epam.itemcatalog.repository.specification.impl.item.FindAllItemCriteriaSpecification;
import com.epam.itemcatalog.repository.specification.impl.item.FindItemByIdSpecification;
import com.epam.itemcatalog.repository.specification.impl.item.FindItemByNameCriteriaSpecification;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RepositoryConfiguration.class, HibernateConfiguration.class, EmbeddedDbConfiguration.class})
public class ItemEntityRepositoryImplTest {

    @Autowired
    private EntityRepository<Item> repository;

    private Item testItem = new Item();

    {
        testItem.setName("item");
        testItem.setPrice(new BigDecimal("1.0"));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldAddItem() {
        int expectedFieldsCount = repository.findBy(new FindAllItemCriteriaSpecification()).size() + 1;
        repository.save(testItem);
        int actualFieldsCount = repository.findBy(new FindAllItemCriteriaSpecification()).size();

        Assert.assertEquals(expectedFieldsCount, actualFieldsCount);
    }

    @Test
    @Transactional
    @Rollback
    public void shouldUpdateItem() {
        repository.save(testItem);
        Item expectedItem = repository.findSingleBy(new FindItemByNameCriteriaSpecification(testItem.getName())).get();
        expectedItem.setName("Not item");

        Assert.assertNotEquals(testItem, expectedItem);
    }


    @Test
    @Transactional
    @Rollback
    public void shouldDeleteItem() {
        repository.save(testItem);
        int expectedFieldsCount = repository.findBy(new FindAllItemCriteriaSpecification()).size() - 1;

        Item expectedItem = repository.findSingleBy(new FindItemByNameCriteriaSpecification(testItem.getName())).get();
        repository.remove(expectedItem.getId());
        int actualFieldsCount = repository.findBy(new FindAllItemCriteriaSpecification()).size();


        Assert.assertEquals(expectedFieldsCount, actualFieldsCount);
    }

    @Test
    @Transactional
    public void shouldFindAll() {
        List<Item> items = repository.findBy(new FindAllItemCriteriaSpecification());

        Assert.assertTrue(items.size() > 0);
    }

    @Test
    @Transactional
    public void shouldFindById() {
        repository.save(testItem);

        Item expectedItem = repository.findSingleBy(new FindItemByNameCriteriaSpecification(testItem.getName())).get();
        Optional<Item> optionalItem = repository.findSingleBy(new FindItemByIdSpecification(expectedItem.getId()));

        Assert.assertTrue(optionalItem.isPresent());
    }

    @Test
    @Transactional
    public void shouldFindByName() {
        repository.save(testItem);

        Optional<Item> optionalItem = repository.findSingleBy(new FindItemByNameCriteriaSpecification(testItem.getName()));

        Assert.assertTrue(optionalItem.isPresent());
    }
}

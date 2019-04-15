package com.epam.itemcatalog.repository.impl;

import com.epam.itemcatalog.model.entity.Currency;
import com.epam.itemcatalog.repository.EntityRepository;
import com.epam.itemcatalog.repository.config.EmbeddedDbConfiguration;
import com.epam.itemcatalog.repository.config.HibernateConfiguration;
import com.epam.itemcatalog.repository.config.RepositoryConfiguration;
import com.epam.itemcatalog.repository.specification.impl.currency.FindAllCurrencyCriteriaSpecification;
import com.epam.itemcatalog.repository.specification.impl.currency.FindCurrencyByCodeSpecification;
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
public class CurrencyItemEntityRepositoryImplTest {

    @Autowired
    private EntityRepository<Currency> repository;

    private Currency testCurrency = new Currency();

    {
        testCurrency.setId(null);
        testCurrency.setCode("AAA");
        testCurrency.setRate(new BigDecimal("1.0"));
    }

    @Test
    @Transactional
    @Rollback
    public void shouldAddCurrency() {
        int expectedFieldsCount = repository.findBy(new FindAllCurrencyCriteriaSpecification()).size() + 1;
        repository.save(testCurrency);
        int actualFieldsCount = repository.findBy(new FindAllCurrencyCriteriaSpecification()).size();

        Assert.assertEquals(expectedFieldsCount, actualFieldsCount);
    }

    @Test
    @Transactional
    @Rollback
    public void shouldUpdateCurrency() {
        repository.save(testCurrency);
        Currency expectedCurrency = repository.findSingleBy(new FindCurrencyByCodeSpecification(testCurrency.getCode())).get();
        expectedCurrency.setCode("BBB");

        Assert.assertNotEquals(testCurrency, expectedCurrency);
    }


    @Test
    @Transactional
    @Rollback
    public void shouldDeleteCurrency() {
        repository.save(testCurrency);
        int expectedFieldsCount = repository.findBy(new FindAllCurrencyCriteriaSpecification()).size() - 1;

        Currency expectedCurrency = repository.findSingleBy(new FindCurrencyByCodeSpecification(testCurrency.getCode())).get();
        repository.remove(expectedCurrency.getId());
        int actualFieldsCount = repository.findBy(new FindAllCurrencyCriteriaSpecification()).size();

        Assert.assertEquals(expectedFieldsCount, actualFieldsCount);
    }

    @Test
    @Transactional
    public void shouldFindAll() {
        List<Currency> currencies = repository.findBy(new FindAllCurrencyCriteriaSpecification());

        Assert.assertTrue(currencies.size() > 0);
    }

    @Test
    @Transactional
    public void shouldFindByCode() {
        repository.save(testCurrency);

        Optional<Currency> optionalCurrency = repository.findSingleBy(new FindCurrencyByCodeSpecification(testCurrency.getCode()));

        Assert.assertTrue(optionalCurrency.isPresent());
    }
}

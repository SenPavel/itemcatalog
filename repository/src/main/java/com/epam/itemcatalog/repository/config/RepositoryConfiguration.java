package com.epam.itemcatalog.repository.config;

import com.epam.itemcatalog.model.entity.Currency;
import com.epam.itemcatalog.model.entity.Item;
import com.epam.itemcatalog.repository.EntityRepository;
import com.epam.itemcatalog.repository.impl.EntityRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfiguration {

    @Bean
    public EntityRepository<Item> itemRepository() {
        return new EntityRepositoryImpl<>(Item.class);
    }

    @Bean
    public EntityRepository<Currency> currencyRepository() {
        return new EntityRepositoryImpl<>(Currency.class);
    }
}

package com.epam.itemcatalog.service.config;

import com.epam.itemcatalog.model.entity.Currency;
import com.epam.itemcatalog.model.entity.Item;
import com.epam.itemcatalog.repository.EntityRepository;
import com.epam.itemcatalog.repository.config.RepositoryConfiguration;
import com.epam.itemcatalog.service.CurrencyService;
import com.epam.itemcatalog.service.ItemService;
import com.epam.itemcatalog.service.converter.CurrencyConverter;
import com.epam.itemcatalog.service.converter.config.CurrencyConverterConfiguration;
import com.epam.itemcatalog.service.impl.CurrencyServiceImpl;
import com.epam.itemcatalog.service.impl.ItemServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.epam.itemcatalog.repository.config")
@Import({RepositoryConfiguration.class, CurrencyConverterConfiguration.class})
public class ServiceConfiguration {

    private EntityRepository<Item> itemRepository;
    private EntityRepository<Currency> currencyRepository;

    private CurrencyConverter converter;

    public ServiceConfiguration(EntityRepository<Item> itemRepository, EntityRepository<Currency> currencyRepository,
                                CurrencyConverter converter) {
        this.itemRepository = itemRepository;
        this.currencyRepository = currencyRepository;
        this.converter = converter;
    }

    @Bean
    public ItemService itemService() {
        return new ItemServiceImpl(itemRepository, converter);
    }

    @Bean
    public CurrencyService currencyService() {
        return new CurrencyServiceImpl(currencyRepository);
    }
}

package com.epam.itemcatalog.service.converter.config;

import com.epam.itemcatalog.service.converter.CurrencyConverter;
import com.epam.itemcatalog.service.converter.impl.CurrencyConverterImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrencyConverterConfiguration {

    @Bean
    public CurrencyConverter converter() {
        return new CurrencyConverterImpl();
    }
}

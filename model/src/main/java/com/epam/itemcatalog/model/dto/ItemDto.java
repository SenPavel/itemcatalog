package com.epam.itemcatalog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto extends BaseDto<Long> {

    @NotEmpty(message = "Item name must be not empty")
    @Size(min = ValidationProperties.MIN_NAME_SIZE, max = ValidationProperties.MAX_NAME_SIZE)
    private String name;

    @NotNull(message = "Item price must be not null")
    @DecimalMin(ValidationProperties.MIN_PRICE_SIZE)
    @DecimalMax(ValidationProperties.MAX_PRICE_SIZE)
    private BigDecimal price;

    private final class ValidationProperties {
        private static final int MIN_NAME_SIZE = 1;
        private static final int MAX_NAME_SIZE = 25;

        private static final String MIN_PRICE_SIZE = "1";
        private static final String MAX_PRICE_SIZE = "100000";

        private ValidationProperties() {
        }
    }
}

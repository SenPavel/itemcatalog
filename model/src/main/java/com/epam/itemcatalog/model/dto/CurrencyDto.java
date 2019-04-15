package com.epam.itemcatalog.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyDto extends BaseDto<Long> {

    @NotEmpty(message = "Currency code must be not empty")
    private String code;

    @NotNull(message = "Currency rate must be not empty")
    private BigDecimal rate;
}

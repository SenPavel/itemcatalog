package com.epam.itemcatalog.model.dto;

import lombok.Data;

@Data
public abstract class BaseDto<T> {

    protected T id;
}

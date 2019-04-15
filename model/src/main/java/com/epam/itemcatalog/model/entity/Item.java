package com.epam.itemcatalog.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item", schema = "item_catalog")
public class Item extends BaseEntity<Long> implements Serializable {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "price", nullable = false, scale = 2)
    private BigDecimal price;
}

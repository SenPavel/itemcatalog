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
@Table(name = "currency", schema = "item_catalog")
public class Currency extends BaseEntity<Long> implements Serializable {

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "rate", nullable = false)
    private BigDecimal rate;
}

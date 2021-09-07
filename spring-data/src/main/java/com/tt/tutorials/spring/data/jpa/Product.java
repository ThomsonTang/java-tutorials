package com.tt.tutorials.spring.data.jpa;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

/**
 * The Product domain class.
 *
 * @author Thomson Tang
 * @version Created: 23/09/2017.
 */
@Entity
public class Product extends AbstractEntity {

    @Column(nullable = false)
    private String name;
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @ElementCollection
    private Map<String, String> attributes = new HashMap<>();

    public Product(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    protected Product() {
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }
}

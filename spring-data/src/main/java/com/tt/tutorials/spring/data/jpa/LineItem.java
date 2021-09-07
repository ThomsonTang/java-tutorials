package com.tt.tutorials.spring.data.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * The LineItem domain class.
 *
 * @author Thomson Tang
 * @version Created: 23/09/2017.
 */
@Entity
public class LineItem extends AbstractEntity {

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private BigDecimal price;
    private int amount;

    public LineItem(Product product, int amount) {
        this.product = product;
        this.price = product.getPrice();
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }
}

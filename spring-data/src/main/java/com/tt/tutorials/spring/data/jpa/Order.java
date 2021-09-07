package com.tt.tutorials.spring.data.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 类说明
 *
 * @author Thomson Tang
 * @version Created: 23/09/2017.
 */
@Entity
@Table(name = "Orders")
public class Order extends AbstractEntity {

    @ManyToOne(optional = false)
    private Customer customer;
    @ManyToOne
    private Address billingAddress;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Address shippingAddress;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private Set<LineItem> lineItems = new HashSet<>();

    public Order(Customer customer, Address shippingAddress) {
        this(customer, shippingAddress, null);
    }

    public Order(Customer customer, Address shippingAddress, Address billingAddress) {
        this.customer = customer;
        this.shippingAddress = shippingAddress.getCopy();
        this.billingAddress = billingAddress == null ? null : billingAddress.getCopy();
    }

    protected Order() {
    }

    public void add(LineItem lineItem) {
        this.lineItems.add(lineItem);
    }

    public Customer getCustomer() {
        return customer;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public Set<LineItem> getLineItems() {
        return lineItems;
    }
}

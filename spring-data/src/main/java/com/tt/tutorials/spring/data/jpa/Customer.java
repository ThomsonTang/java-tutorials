package com.tt.tutorials.spring.data.jpa;

import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * The customer domain class.
 *
 * @author Thomson Tang
 * @version Created: 22/09/2017.
 */
@Entity
public class Customer extends AbstractEntity {
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private EmailAddress emailAddress;

    //表示一个customer可以有多个address
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "customer_id")
    private Set<Address> addresses = new HashSet<>();

    public Customer() {
    }

    public Customer(String firstName, String lastName) {
        Assert.hasText(firstName, "first name must not be empty.");
        Assert.hasText(lastName, "last name must not be empty.");

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void add(Address address) {
        Assert.notNull(address, "the address must not be null.");
        this.addresses.add(address);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress=" + emailAddress +
                ", addresses=" + addresses +
                '}';
    }
}

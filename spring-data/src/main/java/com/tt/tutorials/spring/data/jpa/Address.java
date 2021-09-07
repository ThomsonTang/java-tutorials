package com.tt.tutorials.spring.data.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 地址实体类
 *
 * 将会和数据库中的表做映射
 *
 * @author Thomson Tang
 * @version Created: 22/09/2017.
 */
@Entity
public class Address extends AbstractEntity {
    private String street;
    private String city;

    @Column(name = "country") //通过该注解指定表中列的名字，默认就是属性名
    private String country;

    public Address() {
    }

    public Address(String street, String city, String country) {
        this.street = street;
        this.city = city;
        this.country = country;
    }

    public Address getCopy() {
        return new Address(this.street, this.city, this.country);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}

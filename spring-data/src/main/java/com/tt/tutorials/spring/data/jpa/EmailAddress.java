package com.tt.tutorials.spring.data.jpa;

import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 邮箱地址实体类
 *
 * 该类是一个值对象。值对象通常用来表示一种类似于基本类型的领域概念，同时可以在值对象中实现领域约束条件。
 *
 * @author Thomson Tang
 * @version Created: 22/09/2017.
 */
@Embeddable
public class EmailAddress {
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

    @Column(name = "email")
    private String emailAddress;

    public EmailAddress(String emailAddress) {
//        Assert.isTrue(isValid(emailAddress), "Invalid email address!");
        this.emailAddress = emailAddress;
    }

    protected EmailAddress() {
    }

    public static boolean isValid(String candidate) {
        return PATTERN.matcher(candidate).matches();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString() {
        return emailAddress;
    }
}

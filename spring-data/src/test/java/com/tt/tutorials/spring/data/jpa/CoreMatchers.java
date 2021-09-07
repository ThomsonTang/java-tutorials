package com.tt.tutorials.spring.data.jpa;

import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

/**
 * 自定义Matcher，方便断言的使用
 *
 * @author Thomson Tang
 */
public class CoreMatchers {

    public static <T> Matcher<T> with(Matcher<T> matcher) {
        return matcher;
    }

    public static Matcher<Product> named(String name) {
        return hasProperty("name", is(name));
    }
}

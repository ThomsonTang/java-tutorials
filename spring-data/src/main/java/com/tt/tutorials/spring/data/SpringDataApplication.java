package com.tt.tutorials.spring.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * spring boot的主程序入口方法
 *
 * @author Thomson Tang
 */
public class SpringDataApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringDataApplication.class);

    public static void main(String[] args) {
        LOGGER.info("test the logback version.");
    }
}

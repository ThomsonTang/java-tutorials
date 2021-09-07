package com.tt.tutorials.spring.data.jpa;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;

import javax.sql.DataSource;

/**
 * Base class for integration tests.
 *
 * @author Thomson Tang
 * @version Created: 23/09/2017.
 */
@DataJpaTest
@Transactional
public abstract class AbstractIntegrationTest {

    @Autowired
    DataSource dataSource;

    public void populateDatabase() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("data.sql"));

        Connection connection = null;
        try {
            connection = DataSourceUtils.getConnection(dataSource);
            populator.populate(connection);
        } finally {
            if (connection != null) {
                DataSourceUtils.releaseConnection(connection, dataSource);
            }
        }
    }
}

package com.tt.tutorials.spring.data.jpa;


import com.tt.tutorials.spring.data.ApplicationConfig;
import com.tt.tutorials.spring.data.repo.CustomerRepository;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Bootstrapping the sample code with tests.
 *
 * @author Thomson Tang
 * @version Created: 23/09/2017.
 */
@DataJpaTest
@ContextConfiguration(classes = ApplicationConfig.class)
public class CustomerRepositoryIntegrationTests extends AbstractIntegrationTest{

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void saveCustomerCorrectly() {
        EmailAddress emailAddress = new EmailAddress("clover@ihabitat.com");

        Customer clover = new Customer("Clover", "Zhu");
        clover.setEmailAddress(emailAddress);
        clover.add(new Address("3rd South", "Xi'an", "China"));

        Customer result = customerRepository.save(clover);
        Assert.assertThat(result.getId(), is(notNullValue()));

        customerRepository.findByEmailAddress(new EmailAddress("clover@ihabitat.com"));
        Assert.assertThat(result, is(clover));
    }
}

package com.tt.tutorials.spring.data.jpa;

import com.thomson.tutorials.spring.data.ApplicationConfig;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;

import static com.thomson.tutorials.spring.data.jpa.CoreMatchers.named;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * {@code ProductRepository}的集成测试用例
 *
 * @author Thomson Tang
 */
@ContextConfiguration(classes = ApplicationConfig.class)
public class ProductRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    ProductRepository repository;

    @Test
    public void createProduct() {
        Product iPhone = new Product("iPhone", new BigDecimal(6888.00));
        iPhone = repository.save(iPhone);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void lookupProductsByDescription() {
        Pageable pageable = new PageRequest(0, 1, Sort.Direction.DESC, "name");
        Page<Product> page = repository.findByDescriptionContaining("Apple", pageable);

        assertThat(page.getContent(), hasSize(1));
        assertThat(page, Matchers.hasItems(named("iPhone")));
    }


}

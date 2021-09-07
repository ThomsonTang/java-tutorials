package com.tt.tutorials.spring.data.repo;

import com.thomson.tutorials.spring.data.jpa.Customer;
import com.thomson.tutorials.spring.data.jpa.EmailAddress;

import org.springframework.data.repository.Repository;

/**
 * Repository interface definition for Customers.
 *
 * 通过扩展Spring Data的标记接口，{@code CustomerRepository}才能使用Spring Data提供的各种基础功能。
 *
 * @author Thomson Tang
 * @version Created: 22/09/2017.
 */
public interface CustomerRepository extends Repository<Customer, Long> {

    /**
     * 根据ID查询客户
     *
     * @param id 要查找的ID
     * @return Customer instance
     */
    Customer findOne(Long id);

    /**
     * 保存给定的{@link Customer}
     *
     * @param customer 要保存的customer
     * @return 已保存的customer
     */
    Customer save(Customer customer);

    /**
     * 根据邮箱地址查找{@link Customer}
     *
     * @param emailAddress 邮箱地址
     * @return
     */
    Customer findByEmailAddress(EmailAddress emailAddress);
}

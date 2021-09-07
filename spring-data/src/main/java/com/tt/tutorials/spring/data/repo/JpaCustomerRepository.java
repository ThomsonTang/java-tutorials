package com.tt.tutorials.spring.data.repo;

import com.thomson.tutorials.spring.data.jpa.Customer;
import com.thomson.tutorials.spring.data.jpa.EmailAddress;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Traditional repository implementation for Customers.
 *
 * @author Thomson Tang
 * @version Created: 23/09/2017.
 */
@Repository
public class JpaCustomerRepository implements CustomerRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Customer findOne(Long id) {
        return em.find(Customer.class, id);
    }

    @Override
    @Transactional
    public Customer save(Customer account) {
        if (account.getId() == null) {
            em.persist(account);
            return account;
        } else {
            return em.merge(account);
        }
    }

    @Override
    public Customer findByEmailAddress(EmailAddress emailAddress) {
        TypedQuery<Customer> query = em.createQuery("select c from Customer c where c.email = :emailAddres", Customer.class);
        query.setParameter("emailAddress", emailAddress);
        return query.getSingleResult();
    }
}

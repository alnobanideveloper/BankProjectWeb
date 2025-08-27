package com.eastnets.dao;



import com.eastnets.model.Customer;
import jakarta.persistence.EntityManager;

import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public class CustomerDAO {

    @PersistenceContext
    private EntityManager em;  // injected by Spring, transaction-aware


    //return the added customer
    public Optional<Customer> createCustomer(Customer customer) {
        em.persist(customer);
        return Optional.of(customer);

    }


    public Optional<Customer> getCustomerByID(String id) {
        return Optional.ofNullable(em.find(Customer.class, id));
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return Optional.of(em.createQuery("select c from Customer c where c.email =:email", Customer.class)
                .setParameter("email", email)
                .getSingleResultOrNull());
    }

    public Optional<Customer> getCustomerByEmailAndPassword(String email, String password) {

        return Optional.ofNullable(
                em.createQuery("SELECT c FROM Customer c WHERE c.email = :email AND c.password = :password", Customer.class)
                        .setParameter("email", email)
                        .setParameter("password", password)
                        .getSingleResultOrNull()
        );

    }

    public int deleteCustomerByID(String id) {
        Customer customer = em.find(Customer.class, id);
        if (customer != null) {
            em.remove(customer);
            return 1;
        } else {
            return 0;
        }
    }

    public int editCustomerByID(String id, Customer customer) {
        Customer updatedCustomer = em.find(Customer.class , id) ;
        if(updatedCustomer == null)
            return 0;

        em.merge(customer);
        return 1;
    }
}

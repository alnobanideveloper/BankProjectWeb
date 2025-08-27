package com.eastnets.dao;



import com.eastnets.model.Account;
import com.eastnets.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountDAO  {
    @PersistenceContext
    private EntityManager em;  // injected by Spring, transaction-aware

    public List<Account> getAllAccounts(String customerID)  {
        Customer customer = em.find(Customer.class  , customerID);
        Hibernate.initialize(customer.getAccounts());

        if(customer == null)
            return List.of();

        return customer.getAccounts();
    }

    public Optional<Account> getAccount(int accountNumber){
       return Optional.ofNullable(em.find(Account.class , accountNumber));
    }

    public Optional<Account> createAccount(Account account){
       em.persist(account);

        return Optional.of(account);
    }


    public int editAccount(int accountNumber, Account account) {
        Account acc = em.find(Account.class, accountNumber);
        if (account != null) {
            em.merge(account);
            return 1;
        }
        return 0;
    }

    public int deleteAccount(int accountNumber){
        Account account = em.find(Account.class, accountNumber);
        if (account != null) {
            em.remove(account);
            return 1;
        }
            return 0;
    }
}

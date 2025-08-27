package com.eastnets.dao;

import com.eastnets.model.Transaction;
import com.eastnets.util.DBConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionDAO{
    @PersistenceContext
    private EntityManager em;  // injected by Spring, transaction-aware
    public List<Transaction> getAllTransactionsForAccount(int accountNumber) {
        String jpql = "SELECT t FROM Transaction t WHERE t.sourceNumber = :accountNumber OR t.destinationNumber = :accountNumber";

        return em.createQuery(jpql, Transaction.class)
                .setParameter("accountNumber", accountNumber)
                .getResultList();
    }

    public Optional<Transaction> getTransaction(int transactionID){
      return Optional.ofNullable(em.find(Transaction.class , transactionID));
    }

    public Optional<Transaction> addTransaction(Transaction transaction) {
        em.persist(transaction);
        return Optional.of(transaction);
    }



}

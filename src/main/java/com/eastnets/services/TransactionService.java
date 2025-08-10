package com.eastnets.services;



import com.eastnets.dao.TransactionDAO;
import com.eastnets.model.Transaction;

import java.util.List;

public class TransactionService {
    TransactionDAO transactionDAO;

    public TransactionService(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }


    public List<Transaction> getAllTransactions(int accountNumber){
        try {
            return transactionDAO.getAllTransactionsForAccount(accountNumber);
        }catch (Exception e){
            throw new RuntimeException("Database error" );
        }
    }
}

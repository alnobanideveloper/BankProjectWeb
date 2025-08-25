package com.eastnets.services;



import com.eastnets.dao.TransactionDAO;
import com.eastnets.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

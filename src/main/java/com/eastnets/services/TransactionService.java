package com.eastnets.services;



import com.eastnets.dao.TransactionDAO;
import com.eastnets.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TransactionService {
    TransactionDAO transactionDAO;

    public TransactionService(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }


    public List<Transaction> getAllTransactions(int accountNumber){
            return transactionDAO.getAllTransactionsForAccount(accountNumber);
    }
}

package com.eastnets.services;


import com.eastnets.dao.AccountDAO;
import com.eastnets.dao.TransactionDAO;
import com.eastnets.exceptions.InvalidFieldException;
import com.eastnets.model.Account;
import com.eastnets.model.Transaction;
import com.eastnets.validation.ValidationStrategy;
import com.eastnets.validation.Validator;
import com.eastnets.validation.strategies.validateBalance;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class AccountService {
    AccountDAO accountDao;
    TransactionDAO transactionDao;

    public AccountService(AccountDAO accountDao, TransactionDAO transactionDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
    }


    public Optional<Account> createAccount(Account account) {
        validateAccount(account);

        return accountDao.createAccount(account);
    }


    public Account getAccountByNumber(int accountNumber) {
            Optional<Account> account =  accountDao.getAccount(accountNumber);
            if(account.isEmpty()){
                throw new InvalidFieldException("invalid account number");
            }

            return account.get();
    }

    public List<Account> getAllAccounts(String customerId) {
            return accountDao.getAllAccounts(customerId);
    }


    public int editAccount(Account account) {
        validateAccount(account);
            return accountDao.editAccount(account.getAccountNo(), account);
    }


    public int deleteAccount(Account account) {
            return accountDao.deleteAccount(account.getAccountNo());
    }





    public float withdraw(Account account, float amount) {
        checkTransferAmount(amount);
        Account updatedAccount = updateBalance(account, amount);
            accountDao.editAccount(account.getAccountNo(), updatedAccount);
            transactionDao.addTransaction(new Transaction(amount, null, updatedAccount.getAccountNo(), "withdraw"));

        return updatedAccount.getBalance();

    }

    public float deposit(Account account, float amount) {
        checkTransferAmount(amount);
        float newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
            accountDao.editAccount(account.getAccountNo(), account);
            transactionDao.addTransaction(new Transaction(amount, account.getAccountNo(), null, "deposite"));


        return newBalance;
    }


    public float transfer(float amount, Account fromAccount, Account toAccount) {
        checkTransferAmount(amount);

        // Deduct from source
        Account updatedFrom = updateBalance(fromAccount, amount);
        validateAccount(updatedFrom);
        // Add to destination
        toAccount.setBalance(toAccount.getBalance() + amount);

            accountDao.editAccount(fromAccount.getAccountNo(), updatedFrom);
            accountDao.editAccount(toAccount.getAccountNo(), toAccount);

            // One transaction with both source and destination
            transactionDao.addTransaction(new Transaction(amount, toAccount.getAccountNo(), fromAccount.getAccountNo(), "transfer"));



        return updatedFrom.getBalance();
    }


    private Account updateBalance(Account account, float delta) {
        Account updated = new Account(account);
        updated.setBalance(account.getBalance() - delta);
        validateAccount(updated);
        return updated;
    }

    private void checkTransferAmount(float ammount) {
        if (ammount < 0)
            throw new InvalidFieldException("Transfer amount must be greater than 0");
    }


    private void validateAccount(Account account) {
        List<ValidationStrategy> validations = List.of(new validateBalance());
        Validator validator = new Validator(validations);
        validator.validate(account);

    }
}

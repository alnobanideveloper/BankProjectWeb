package com.eastnets.services;


import com.eastnets.dao.AccountDAO;
import com.eastnets.dao.TransactionDAO;
import com.eastnets.exceptions.InvalidFieldException;
import com.eastnets.model.Account;
import com.eastnets.model.Transaction;
import com.eastnets.validation.ValidationStrategy;
import com.eastnets.validation.Validator;
import com.eastnets.validation.strategies.validateBalance;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    AccountDAO accountDao;
    TransactionDAO transactionDao;

    public AccountService(AccountDAO accountDao, TransactionDAO transactionDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
        System.out.println("in account service");
    }


    public Optional<Account> createAccount(Account account) {
        validateAccount(account);
        try {
            return accountDao.createAccount(account);
        } catch (SQLException e) {
            throw new RuntimeException("Database Error" + e.getMessage());
        }
    }


    public Account getAccountByNumber(int accountNumber) {
        try {
            Optional<Account> account =  accountDao.getAccount(accountNumber);
            if(account.isEmpty()){
                throw new InvalidFieldException("invalid account number");
            }

            return account.get();
        } catch (SQLException e) {
            throw new RuntimeException("Database Error");
        }
    }

    public List<Account> getAllAccounts(String customerId) {
        try {
            return accountDao.getAllAccounts(customerId);
        } catch (SQLException e) {
            throw new RuntimeException("Database Error");
        }
    }


    public int editAccount(Account account) {
        validateAccount(account);
        try {
            return accountDao.editAccount(account.getAccountNo(), account);
        } catch (SQLException e) {
            throw new RuntimeException("Database Error");
        }
    }


    public int deleteAccount(Account account) {
        try {
            return accountDao.deleteAccount(account.getAccountNo());
        } catch (SQLException e) {
            throw new RuntimeException("Database Error");
        }
    }


    public int deleteAllAccounts(String customerId) {
        try {
            return accountDao.deleteAllAccounts(customerId);
        } catch (SQLException e) {
            throw new RuntimeException("Database Error");
        }
    }


    public double withdraw(Account account, double amount) {
        checkTransferAmount(amount);
        Account updatedAccount = updateBalance(account, amount);
        try {
            accountDao.editAccount(account.getAccountNo(), updatedAccount);
            transactionDao.addTransaction(new Transaction(amount, null, updatedAccount.getAccountNo(), "withdraw"));
        } catch (SQLException e) {
            throw new RuntimeException("Database Error");
        }
        return updatedAccount.getBalance();

    }

    public double deposit(Account account, double amount) {
        checkTransferAmount(amount);
        double newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        try {
            accountDao.editAccount(account.getAccountNo(), account);
            transactionDao.addTransaction(new Transaction(amount, account.getAccountNo(), null, "deposite"));

        } catch (SQLException e) {
            throw new RuntimeException("Database Error");
        }
        return newBalance;
    }


    public double transfer(double amount, Account fromAccount, Account toAccount) {
        checkTransferAmount(amount);

        // Deduct from source
        Account updatedFrom = updateBalance(fromAccount, amount);
        validateAccount(updatedFrom);
        // Add to destination
        toAccount.setBalance(toAccount.getBalance() + amount);

        try {
            accountDao.editAccount(fromAccount.getAccountNo(), updatedFrom);
            accountDao.editAccount(toAccount.getAccountNo(), toAccount);

            // One transaction with both source and destination
            transactionDao.addTransaction(new Transaction(amount, toAccount.getAccountNo(), fromAccount.getAccountNo(), "transfer"));

        } catch (SQLException e) {
            throw new RuntimeException("Database Error", e);
        }

        return updatedFrom.getBalance();
    }


    private Account updateBalance(Account account, double delta) {
        Account updated = new Account(account);
        updated.setBalance(account.getBalance() - delta);
        validateAccount(updated);
        return updated;
    }

    private void checkTransferAmount(double ammount) {
        if (ammount < 0)
            throw new InvalidFieldException("Transfer amount must be greater than 0");
    }


    private void validateAccount(Account account) {
        List<ValidationStrategy> validations = List.of(new validateBalance());
        Validator validator = new Validator(validations);
        validator.validate(account);

    }
}

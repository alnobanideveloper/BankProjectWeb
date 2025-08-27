package com.eastnets.web;

import com.eastnets.model.Account;
import com.eastnets.model.Transaction;
import com.eastnets.services.AccountService;
import com.eastnets.services.CustomerService;
import com.eastnets.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")

public class TransactionController {
    private final TransactionService transactionService;
    private final AccountService accountService;


    public TransactionController (TransactionService transactionService , AccountService accountService){
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable("accountNumber") int accountNumber){
        Account account = accountService.getAccountByNumber(accountNumber);
        List<Transaction> transactions = transactionService.getAllTransactions(accountNumber);

        return  ResponseEntity.ok(transactions);
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<Float> withdraw(@PathVariable ("accountNumber") int accountNumber , @RequestParam("amount") float amount){
        Account account = accountService.getAccountByNumber(accountNumber);
        float newBalance = accountService.withdraw(account , amount);

        return  ResponseEntity.ok(newBalance);
    }

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<Float> deposit(@PathVariable ("accountNumber") int accountNumber , @RequestParam("amount") float amount){
        Account account = accountService.getAccountByNumber(accountNumber);
        float newBalance = accountService.deposit(account , amount);

        return  ResponseEntity.ok(newBalance);
    }

    @PostMapping("/{accountNumber}/transfer")
    public ResponseEntity<Float> transfer(@PathVariable ("accountNumber") int accountNumber ,
                                          @RequestParam("amount") float amount ,
                                          @RequestParam("toAccount") int toAccountNum){

        Account account = accountService.getAccountByNumber(accountNumber);
        Account toAccount = accountService.getAccountByNumber(toAccountNum);
        float newBalance = accountService.transfer(amount , account , toAccount);

        return  ResponseEntity.ok(newBalance);
    }


}

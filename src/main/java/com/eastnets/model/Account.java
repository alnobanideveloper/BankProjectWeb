package com.eastnets.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name = "account")
public class Account {


    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference
    protected Customer customer;

    @Column(name="account_type")
    protected String accountType;

    @Id
    @Column(name = "account_number")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int accountNo;

    @Column(name = "balance")
    protected float balance;


    // No-argument constructor (needed by frameworks)
    public Account() {}

    // All-arguments constructor
    public Account(String accountType, float balance , Customer customer ) {

        this.accountType = accountType;
        this.balance = balance;
        this.customer = customer;
    }

    public Account(Account other) {
        this.customer = other.customer;
        this.accountType = other.accountType;
        this.accountNo = other.accountNo;
        this.balance = other.balance;
    }

    // Getters and Setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer= customer;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getAccountNo() {
        return accountNo;
    }


    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }



    @Override
    public String toString() {
        return "{Account Number : " + this.getAccountNo() + " Balance : "  + this.getBalance() + " }";
    }
}

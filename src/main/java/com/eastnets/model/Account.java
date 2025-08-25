package com.eastnets.model;

import java.util.Date;

public class Account {

    protected String nationalID;
    protected String accountType;
    protected int accountNo;
    protected double balance;
    protected Date openedDate;

    // No-argument constructor (needed by frameworks)
    public Account() {}

    // All-arguments constructor
    public Account(String accountType, double balance , String nationalID ) {

        this.accountType = accountType;
        this.balance = balance;
        this.nationalID = nationalID;
    }

    public Account(Account other) {
        this.nationalID = other.nationalID;
        this.accountType = other.accountType;
        this.accountNo = other.accountNo;
        this.balance = other.balance;
        this.openedDate = other.openedDate;
    }

    // Getters and Setters
    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getOpenedDate() {
        return openedDate;
    }

    public void setOpenedDate(Date openedDate) {this.openedDate = openedDate;}

    @Override
    public String toString() {
        return "{Account Number : " + this.getAccountNo() + " Balance : "  + this.getBalance() + " }";
    }
}

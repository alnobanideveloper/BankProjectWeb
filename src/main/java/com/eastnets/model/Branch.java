package com.eastnets.model;
import java.util.List;

public class Branch {
    private int number;
    private String name;
    private String address;
    private List<Customer> customers;
    private String bankSwift;



    public Branch() {}

    public Branch(String address, String name, int number , String BankSwift ) {
        this.address = address;
        this.name = name;
        this.number = number;
        this.bankSwift = BankSwift;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public String getBankSwift() {
        return bankSwift;
    }

    public void setBankSwift(String bankSwift) {
        this.bankSwift = bankSwift;
    }


    @Override
    public String toString() {
        return "Branch{" +
                "number=" + number  +
                ", name='" + name + '\'' +
                ", address='" +   address + '\'' +
                '}';
        }
    }

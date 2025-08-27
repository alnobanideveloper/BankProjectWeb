package com.eastnets.model;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int branch_number;
    private String name;
    private String location;

    @ManyToOne
    @JoinColumn(name = "bank_swift")
    private Bank bank;

    public Branch() {}

    public Branch(String location, String name, int number , Bank bank ) {
        this.location = location;
        this.name = name;
        this.branch_number = number;
        this.bank = bank;
    }

    public Branch(String location, String name,Bank bank ) {
        this.location = location;
        this.name = name;
        this.bank = bank;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return location;
    }

    public void setAddress(String address) {
        this.location = address;
    }

    public int getNumber() {
        return branch_number;
    }

    public void setNumber(int number) {
        this.branch_number = number;
    }


    public Bank getBank() {
        return this.bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }


    @Override
    public String toString() {
        return "Branch{" +
                "number=" + branch_number  +
                ", name='" + name + '\'' +
                ", address='" +   location + '\'' +
                '}';
        }
    }

package com.eastnets.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Bank {
    @Id
    private String swift_code;
    private String name;
    private String address;

    public Bank(){

    }

    public Bank(String name, String SWIFT, String address) {
        this.name = name;
        this.swift_code = SWIFT;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSWIFT() {
        return swift_code;
    }

    public void setSWIFT(String SWIFT) {
        this.swift_code = SWIFT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "swiftCode='" + swift_code + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

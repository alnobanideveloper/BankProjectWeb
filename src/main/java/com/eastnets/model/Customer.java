package com.eastnets.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Customer {
    private String phone;
    @Id
    private String nationalId;
    private String name;
    private String email;
    private String address;

    @ManyToOne
    @JoinColumn(name = "branch_number")
    private Branch branch;
    private String password;

    @OneToMany(mappedBy = "customer" , fetch=FetchType.LAZY)
    @JsonManagedReference
    private List<Account> accounts;

    public Customer() { }

    @JsonCreator
    public Customer(
            @JsonProperty("nationalId") String nationalId,
            @JsonProperty("phoneNumber") String phoneNumber,
            @JsonProperty("password") String password,
            @JsonProperty("email") String email,
            @JsonProperty("name") String name,
            @JsonProperty("branch") Branch branch,
            @JsonProperty("address") String address
    ) {
        this.nationalId = nationalId;
        this.phone = phoneNumber;
        this.password = password;
        this.email = email;
        this.name = name;
        this.branch = branch;
        this.address = address;
    }

    private Customer(Builder builder) {
        this.phone = builder.phoneNumber;
        this.nationalId = builder.nationalId;
        this.name = builder.name;
        this.email = builder.email;
        this.address = builder.address;
        this.branch = builder.branch;
        this.password = builder.password;
        this.accounts = new ArrayList<>();
    }

    public String getPhoneNumber() {
        return phone;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phone = phoneNumber;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }


    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "name is :" + name
                + "\nphone number is : " + phone
                + "\nnationalID is : " + nationalId
                + "\naddress is : " + address
                + "\nemail is : " + email;
    }

    public static class Builder{
        private String phoneNumber;
        private String nationalId;
        private String name;
        private String email;
        private String address;
        private Branch branch;
        private String password;

        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder setAddress(String Address){
            this.address = Address;
            return this;
        }


        public Builder setNationalId(String nationalId) {
            this.nationalId = nationalId;
            return this;
        }

        public Builder setBranch(Branch branch) {
            this.branch = branch;
            return this;
        }


        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setName(String name){
            this.name = name;
            return this;
        }

        public Customer build(){
            return new Customer(this);
        }
    }
}

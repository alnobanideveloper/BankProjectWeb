package com.eastnets.model;

import jakarta.persistence.*;

import java.sql.Date;
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int id;
    @Column(name ="source")
    private Integer sourceNumber;
    @Column(name = "destination")
    private Integer destinationNumber;
    private float amount;
    private String type;
    @Column(name = "transaction_date")
    private Date created_at;


    public Transaction(){}

    public Transaction(float amount, Integer destinationNumber, Integer sourceNumber, String type) {
        this.amount = amount;
        this.destinationNumber = destinationNumber;
        this.sourceNumber = sourceNumber;
        this.type = type;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public float getAmount() {
        return amount;
    }


    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Integer getSourceNumber() {
        return sourceNumber;
    }

    public void setSourceNumber(Integer sourceNumber) {
        this.sourceNumber = sourceNumber;
    }

    public Integer getDestinationNumber() {
        return destinationNumber;
    }

    public void setDestinationNumber(Integer destinationNumber) {
        this.destinationNumber = destinationNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Transaction Details:\n" +
                "----------------------\n" +
                "Transaction ID     : " + id + "\n" +
                "Source Account     : " + (sourceNumber != null ? sourceNumber : "N/A") + "\n" +
                "Destination Account: " + (destinationNumber != null ? destinationNumber : "N/A") + "\n" +
                "Amount             : " + String.format("%.2f", amount) + "\n" +
                "Type               : " + type + "\n" +
                "Date & Time        : " + (created_at != null ? created_at : "N/A");
    }


}

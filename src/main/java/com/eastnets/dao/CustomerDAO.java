package com.eastnets.dao;


import com.eastnets.model.Account;
import com.eastnets.model.Customer;
import com.eastnets.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerDAO  {
    AccountDAO accountDAO = new AccountDAO();

    //return the added customer
    public Optional<Customer> createCustomer(Customer customer) throws SQLException{
        String sql = "INSERT INTO CUSTOMER (nationalId, name, email, address, phone, branch_number , password) VALUES (?, ?, ?, ?, ?, ? , ?)";
        Optional<Customer> optionaCustomer = Optional.empty();
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {


            pstmt.setString(1, customer.getNationalId());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getAddress());
            pstmt.setString(5, customer.getPhoneNumber());
//            pstmt.setInt(6, customer.getBranch().getNumber());
            pstmt.setString(7, customer.getPassword());
            pstmt.executeUpdate();

            optionaCustomer = Optional.of(customer);
        }
        return optionaCustomer ;
    }



    public Optional<Customer> getCustomerByID(String id) throws SQLException {
        String sql = "SELECT * FROM CUSTOMER WHERE nationalId = ?";
        Optional<Customer> customer = Optional.empty();

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                customer = Optional.of(new Customer.Builder()
                        .setName(rs.getString("name"))
                        .setEmail(rs.getString("email"))
                        .setPhoneNumber(rs.getString("phone"))
                        .setNationalId(rs.getString("nationalId"))
                        .setAddress(rs.getString("address"))
                        .build());
                List<Account> accounts = accountDAO.getAllAccounts(rs.getString("nationalId"));
                customer.get().setAccounts(accounts);
            }


        }

        return customer;

    }

    public Optional<Customer> getCustomerByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM CUSTOMER WHERE email = ?";
        Optional<Customer> customer = Optional.empty();

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                customer = Optional.of(new Customer.Builder()
                        .setName(rs.getString("name"))
                        .setEmail(rs.getString("email"))
                        .setPhoneNumber(rs.getString("phone"))
                        .setNationalId(rs.getString("nationalId"))
                        .setAddress(rs.getString("address"))
                        .setPassword(rs.getString("password"))
                        .build());
            }
        }

        return customer;

    }

    public Optional<Customer> getCustomerByEmailAndPassword(String email , String password) throws SQLException {

        String sql = "SELECT * FROM CUSTOMER WHERE email = ? and password = ?";
        Optional<Customer> customer = Optional.empty();
        System.out.println("fixing bugs");
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            System.out.println("in getbyemailandpassword");

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                customer = Optional.of(new Customer.Builder()
                        .setName(rs.getString("name"))
                        .setEmail(rs.getString("email"))
                        .setPhoneNumber(rs.getString("phone"))
                        .setNationalId(rs.getString("nationalId"))
                        .setAddress(rs.getString("address"))
                        .build());
                List<Account> accounts = accountDAO.getAllAccounts(rs.getString("nationalId"));
                customer.get().setAccounts(accounts);
                System.out.println(customer.get().getAccounts());
            }
        }
        return customer;
    }

    public int deleteCustomerByID(String id) throws SQLException {
        String sql = "DELETE FROM CUSTOMER WHERE nationalId = ?";
        int rows = 0;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, id);
            rows = stmt.executeUpdate();
        }
        return rows;
    }

    public int editCustomerByID(String id, Customer customer) throws SQLException {
        String sql = "UPDATE CUSTOMER SET name = ?, email = ?, address = ?, phone = ?, branch_number = ? WHERE nationalId = ?";
        int rows = 0;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getPhoneNumber());
//            stmt.setInt(5, customer.getBranch().getNumber());
            stmt.setString(6, id);

            rows = stmt.executeUpdate();
        }
        return rows;
    }



    public List<Customer> getAllCustomers() throws SQLException {
        String sql = "SELECT * FROM CUSTOMER";
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer.Builder()
                        .setName(rs.getString("name"))
                        .setEmail(rs.getString("email"))
                        .setPhoneNumber(rs.getString("phone"))
                        .setNationalId(rs.getString("nationalId"))
                        .setAddress(rs.getString("address"))
                        .build();
                customers.add(customer);
            }
        }
        return customers;
    }

    public List<Customer> getAllCustomersOfBranch(int branchNumber) throws SQLException {
        String sql = "SELECT * FROM CUSTOMER WHERE branch_number = ?";
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, branchNumber);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Customer customer = new Customer.Builder()
                        .setName(rs.getString("name"))
                        .setEmail(rs.getString("email"))
                        .setPhoneNumber(rs.getString("phone"))
                        .setNationalId(rs.getString("nationalId"))
                        .setAddress(rs.getString("address"))
                        .build();
                customers.add(customer);
            }

        }
        return customers;
    }
}

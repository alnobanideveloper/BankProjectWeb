package com.eastnets.dao;



import com.eastnets.model.Account;
import com.eastnets.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountDAO  {


    public List<Account> getAllAccounts(String customerID) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        String sql = "select * from account where customer_id = ?";

        try(Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);){

            stmt.setString(1, customerID);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Account account = new Account(
                        rs.getString("account_type"),
                        rs.getDouble("balance") ,
                        rs.getString("customer_id"));
                account.setAccountNo( rs.getInt("account_number"));

                        accounts.add(account);
            }
        }
        return accounts;
    }

    public Optional<Account> getAccount(int accountNumber) throws SQLException {
        String sql = "select * from account where account_number = ?";
        Optional<Account>  account = Optional.empty();

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);) {

            stmt.setInt(1, accountNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                account = Optional.of( new Account(
                        rs.getString("account_type"),
                        rs.getDouble("balance"),
                        rs.getString("customer_id")));

                account.get().setAccountNo(accountNumber);

            }
        }
        return account;
    }

    public Optional<Account> createAccount(Account account) throws SQLException{
        String sql = "INSERT INTO account (account_type,  balance, customer_id) VALUES (?,  ?, ?)";
        Optional<Account> optionalAcc = Optional.empty();
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, account.getAccountType());
            stmt.setDouble(2, account.getBalance());
            stmt.setString(3, account.getNationalID());

            int rows = stmt.executeUpdate();
            optionalAcc = Optional.of(account);

        }
        return optionalAcc;
    }


    public int editAccount(int accountNumber, Account account) throws SQLException {
        String sql = "UPDATE account SET account_type = ?, balance = ?, customer_id = ? WHERE account_number = ?";
        int rows = 0;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, account.getAccountType());
            stmt.setDouble(2, account.getBalance());
            stmt.setString(3, account.getNationalID());
            stmt.setInt(4, accountNumber);

             rows = stmt.executeUpdate();
        }
        return rows;
    }

    public int deleteAccount(int accountNumber) throws SQLException {
        String sql = "DELETE FROM account WHERE account_number = ?";
        int rows = 0;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, accountNumber);
             rows = stmt.executeUpdate();
        }
        return rows;
    }

    public int deleteAllAccounts(String customerID) throws SQLException {
        String sql = "DELETE FROM account WHERE customer_id = ?";
        int rows = 0;
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, customerID);
             rows = stmt.executeUpdate();
        }
        return rows;
    }

}

package com.eastnets.dao;

import com.eastnets.model.Transaction;
import com.eastnets.util.DBConnection;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionDAO{
    private final DataSource dataSource;
    public TransactionDAO(DataSource dataSource ) {
        this.dataSource = dataSource;
    }
    public List<Transaction> getAllTransactionsForAccount(int accountNumber) throws SQLException {
        String sql = "SELECT * FROM transaction WHERE source = ? OR destination = ?";
        List<Transaction> transactions = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, accountNumber);
            stmt.setInt(2, accountNumber);

            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Transaction transaction = new Transaction(
                        result.getDouble("amount"),
                        result.getInt("destination"),
                        result.getInt("source"),
                        result.getString("type")
                );
                transaction.setId(result.getInt("transaction_id"));
                transaction.setCreated_at(result.getDate("transaction_date"));
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    public Optional<Transaction> getTransaction(int transactionID) throws SQLException {
        String sql = "SELECT * FROM transaction WHERE transaction_id = ?";
        Optional<Transaction> transaction = Optional.empty();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, transactionID);
            ResultSet result = stmt.executeQuery();

            if (result.next()) {
                transaction = Optional.of(new Transaction(
                        result.getDouble("amount"),
                        result.getInt("destination"),
                        result.getInt("source"),
                        result.getString("type")
                ));
                transaction.get().setCreated_at(result.getDate("created_at"));
                transaction.get().setId(result.getInt("transaction_id"));
            }
        } catch (SQLException e) {
            throw new Error("Operation failed: " + e.getMessage());
        }
        return transaction;
    }

    public Optional<Transaction> addTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transaction (source, destination, amount , type) VALUES (?, ?, ? , ?)";
        Optional<Transaction> transactionOptional = Optional.empty();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setObject(1, transaction.getSourceNumber(), Types.INTEGER);
            stmt.setObject(2, transaction.getDestinationNumber(), Types.INTEGER);
            stmt.setDouble(3, transaction.getAmount());
            stmt.setString(4, transaction.getType());

            int result = stmt.executeUpdate();
            if (result > 0)
                transactionOptional = Optional.of(transaction);

        } catch (SQLException e) {
            throw new Error("Operation failed: " + e.getMessage());
        }

        return transactionOptional;
    }


    public List<Transaction> getAllTransactionsByCustomer(int customerID)  throws SQLException {
        return null; // Not implemented yet
    }
}

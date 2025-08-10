package com.eastnets.dao;



import com.eastnets.model.Bank;
import com.eastnets.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BankDAO {


    public List<Bank> getAllBanks() throws  SQLException {
        String sql = "SELECT * FROM bank";
        List<Bank> banks = new ArrayList<>();

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Bank bank = new Bank(
                        rs.getString("name"),
                        rs.getString("swift_code"),
                        rs.getString("address")
                );
                banks.add(bank);
            }
        }
        return banks;
    }

    public Optional<Bank> getBankBySwift(String swift) throws SQLException {
        String sql = "SELECT * FROM bank WHERE swift_code = ?";
        Optional<Bank> bank = Optional.empty();

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, swift);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                bank = Optional.of(new Bank(
                        rs.getString("name"),
                        rs.getString("swift_code"),
                        rs.getString("address"))
                );
            }

        }
        return bank;
    }

}

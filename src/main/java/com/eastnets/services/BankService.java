package com.eastnets.services;



import com.eastnets.dao.BankDAO;
import com.eastnets.model.Bank;

import java.sql.SQLException;
import java.util.List;

public class BankService {
    BankDAO bankDAO;
    public BankService(BankDAO bankDAO){
        this.bankDAO = bankDAO;
    }

    public List<Bank> getAllBanks(){
        try{
            return bankDAO.getAllBanks();
        }catch(SQLException ex){
            throw new RuntimeException("database Error");
        }
    }

}

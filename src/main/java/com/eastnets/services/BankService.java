package com.eastnets.services;



import com.eastnets.dao.BankDAO;
import com.eastnets.model.Bank;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class BankService {
    BankDAO bankDAO;
    public BankService(BankDAO bankDAO){
        this.bankDAO = bankDAO;
    }

    public List<Bank> getAllBanks(){
            return bankDAO.getAllBanks();
    }

}

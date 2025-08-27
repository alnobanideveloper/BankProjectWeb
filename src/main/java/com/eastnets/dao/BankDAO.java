package com.eastnets.dao;

import com.eastnets.model.Bank;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BankDAO {
    @PersistenceContext
    private EntityManager em;  // injected by Spring, transaction-aware

    public List<Bank> getAllBanks() {
       return em.createQuery("SELECT b FROM Bank b", Bank.class).getResultList();
    }

    public Optional<Bank> getBankBySwift(String swift)  {
       return Optional.ofNullable(em.find(Bank.class , swift));
    }

}

package com.eastnets.dao;




import com.eastnets.model.Branch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class BranchDAO  {
    @PersistenceContext
    private EntityManager em;  // injected by Spring, transaction-aware

    public Optional<Branch> getBranch(int branchNumber)  {
        return Optional.ofNullable(em.find(Branch.class , branchNumber));
    }

    public List<Branch> getAllBranchesForBank(String swift) {
            return em.createQuery("SELECT b FROM Branch b WHERE b.bank.swift_code = :swift", Branch.class)
                    .setParameter("swift", swift)
                    .getResultList();
    }
}

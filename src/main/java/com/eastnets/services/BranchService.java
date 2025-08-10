package com.eastnets.services;


import com.eastnets.dao.BranchDAO;
import com.eastnets.model.Branch;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BranchService{
    BranchDAO branchDAO ;

    public BranchService(BranchDAO branchDAO){
        this.branchDAO = branchDAO;
    }


    public List<Branch> getAllBranches(String swiftCode) {
        try{
          return branchDAO.getAllBranchesForBank(swiftCode);
      }catch(SQLException e){
          throw new RuntimeException("database Error");
      }
    }

    public Optional<Branch> getBranchByNumber(int branchNumber) {
        try{
            return branchDAO.getBranch(branchNumber);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

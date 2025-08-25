package com.eastnets.services;


import com.eastnets.dao.BranchDAO;
import com.eastnets.exceptions.InvalidFieldException;
import com.eastnets.model.Branch;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
@Service
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

        public Branch getBranchByNumber(int branchNumber) {
            try{

                Optional<Branch> branch =  branchDAO.getBranch(branchNumber);
                if(branch.isEmpty())
                    throw new InvalidFieldException("invalid branch number");

                return branch.get();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
}

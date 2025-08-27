package com.eastnets.services;


import com.eastnets.dao.BranchDAO;
import com.eastnets.exceptions.InvalidFieldException;
import com.eastnets.model.Branch;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class BranchService{
    BranchDAO branchDAO ;

    public BranchService(BranchDAO branchDAO){
        this.branchDAO = branchDAO;
    }


    public List<Branch> getAllBranches(String swiftCode) {
          return branchDAO.getAllBranchesForBank(swiftCode);

    }

        public Branch getBranchByNumber(int branchNumber) {


            Optional<Branch> branch = branchDAO.getBranch(branchNumber);
            if (branch.isEmpty())
                throw new InvalidFieldException("invalid branch number");

            return branch.get();
        }
}

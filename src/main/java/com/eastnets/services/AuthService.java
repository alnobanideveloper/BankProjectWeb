package com.eastnets.services;

import com.eastnets.dao.CustomerDAO;
import com.eastnets.exceptions.InvalidFieldException;
import com.eastnets.model.Customer;
import com.eastnets.util.validatorUtil;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class AuthService  {
    CustomerDAO customerDAO ;

    public AuthService(CustomerDAO customerDAO  ){
        this.customerDAO = customerDAO; //implement it like this way to prevent tight coupling
    }


    //if its empty, then its invalid credentials
    public Customer signIn(String email, String password) {
        try{
            Optional<Customer> customer =  customerDAO.getCustomerByEmailAndPassword(email , password);
            if(customer.isEmpty())
                throw new InvalidFieldException("invalid credentials");
            return customer.get();
        } catch (SQLException e){
            throw new RuntimeException("Database error" , e);
        }
    }



    public Customer registerCustomer(Customer customer) {
        //validatorUtil.validateCustomer(customer);
        checkIfCustomerExists(customer);
        try{

             Optional<Customer> newCustomer = customerDAO.createCustomer(customer);
             if(newCustomer.isEmpty())
                 throw new InvalidFieldException("invalid fields");
             return newCustomer.get();
        }catch(SQLException e){
            throw new RuntimeException("Database error" , e);
        }
    }

    private void checkIfCustomerExists(Customer customer){
        checkIfCustomerExistsById(customer.getNationalId());
        checkIfCustomerExistsByEmail(customer.getEmail());
    }

    private void checkIfCustomerExistsById(String nationalId) {
        try {
            boolean exists = customerDAO.getCustomerByID(nationalId).isPresent();
            if (exists)
                throw new InvalidFieldException("Customer with this national ID already exists.");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to check customer existence in the database.", e);
        }
    }

    private void checkIfCustomerExistsByEmail(String email) {
        try {
            boolean exists = customerDAO.getCustomerByEmail(email).isPresent();
            if (exists)
                throw new InvalidFieldException("Customer with this email already exists.");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to check customer existence in the database.", e);
        }
    }

}

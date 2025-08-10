package com.eastnets.services;


import com.eastnets.dao.CustomerDAO;
import com.eastnets.model.Customer;
import com.eastnets.util.validatorUtil;

import java.sql.SQLException;
import java.util.Optional;

public class CustomerService{
    CustomerDAO customerDAO ;

    public CustomerService(CustomerDAO customerDAO){
        this.customerDAO = customerDAO;
    }


    public Optional<Customer> getCustomerById(String id) {
        Optional<Customer> customer = Optional.empty();
        try{
            customer = customerDAO.getCustomerByID(id);
        }catch(SQLException e){
            throw new RuntimeException("Unable to fetch customer data. Please try again.", e);
        }
        return customer;
    }


    public int editCustomer(Customer customer){
        int rowsUpdated = 0;
        validatorUtil.validateCustomer( customer);
        try{
            rowsUpdated = customerDAO.editCustomerByID(customer.getNationalId() , customer);
        }catch(SQLException e){
            throw new RuntimeException("Unable to fetch customer data. Please try again.", e);
        }
        return rowsUpdated;
    }

    //a service to delete a customer , if the rowsDeleted = 0 so the id is wrong

    public int deleteCustomer(Customer customer) {
        int rowsDeleted = 0;
        try{
            rowsDeleted = customerDAO.deleteCustomerByID(customer.getNationalId());
        }catch(SQLException e){
            throw new RuntimeException("Unable to fetch customer data. Please try again.", e);
        }
        return rowsDeleted;
    }
}

package com.eastnets.services;


import com.eastnets.dao.CustomerDAO;
import com.eastnets.model.Customer;
import com.eastnets.util.validatorUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CustomerService{
    CustomerDAO customerDAO ;

    public CustomerService(CustomerDAO customerDAO){
        this.customerDAO = customerDAO;
    }


    public Optional<Customer> getCustomerById(String id) {
        Optional<Customer> customer = Optional.empty();
            customer = customerDAO.getCustomerByID(id);

        return customer;
    }
    public Optional<Customer> getCustomerByEmail(String email) {
        Optional<Customer> customer = Optional.empty();
            customer = customerDAO.getCustomerByEmail(email);

        return customer;
    }


    public int editCustomer(Customer customer){
        int rowsUpdated = 0;
        validatorUtil.validateCustomer( customer);

            rowsUpdated = customerDAO.editCustomerByID(customer.getNationalId() , customer);

        return rowsUpdated;
    }

    //a service to delete a customer, if the rowsDeleted = 0 so the id is wrong

    public int deleteCustomer(Customer customer) {
        int rowsDeleted = 0;
            rowsDeleted = customerDAO.deleteCustomerByID(customer.getNationalId());

        return rowsDeleted;
    }
}

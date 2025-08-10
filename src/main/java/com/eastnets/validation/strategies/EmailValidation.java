package com.eastnets.validation.strategies;


import com.eastnets.exceptions.InvalidFieldException;
import com.eastnets.model.Customer;
import com.eastnets.util.StringUtils;
import com.eastnets.validation.ValidationStrategy;

public class EmailValidation implements ValidationStrategy<Customer> {
    public void validate(Customer customer) {
        if(StringUtils.isBlankOrNull(customer.getEmail())) {
            throw new InvalidFieldException("Email must not be empty");
        }
    }
}


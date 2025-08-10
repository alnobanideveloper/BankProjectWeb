package com.eastnets.validation.strategies;


import com.eastnets.exceptions.InvalidFieldException;
import com.eastnets.model.Customer;
import com.eastnets.util.StringUtils;
import com.eastnets.validation.ValidationStrategy;

public class PasswordValidation implements ValidationStrategy<Customer> {
    public void validate(Customer customer) {
        if(StringUtils.isBlankOrNull(customer.getPassword())) {
            throw new InvalidFieldException("Password must not be empty");
        }if(customer.getPassword().length() < 6) {
            throw new InvalidFieldException("Password must be at least 6 characters");
        }
    }
}

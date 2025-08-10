package com.eastnets.validation.strategies;


import com.eastnets.exceptions.InvalidFieldException;
import com.eastnets.model.Customer;
import com.eastnets.util.StringUtils;
import com.eastnets.validation.ValidationStrategy;

public class nameValidation implements ValidationStrategy<Customer> {

    @Override
    public void validate(Customer customer) {
        if (StringUtils.isBlankOrNull(customer.getName())) {
            throw new InvalidFieldException("Customer name cant be empty");
        }
    }
}

package com.eastnets.validation.strategies;


import com.eastnets.exceptions.InvalidFieldException;
import com.eastnets.model.Customer;
import com.eastnets.util.StringUtils;
import com.eastnets.validation.ValidationStrategy;

public class NationalIDValidation implements ValidationStrategy<Customer> {

    @Override
    public void validate(Customer customer) {
        if(StringUtils.isBlankOrNull(customer.getNationalId())) {
            throw new InvalidFieldException("NationalID must not be empty");
        }
    }
}

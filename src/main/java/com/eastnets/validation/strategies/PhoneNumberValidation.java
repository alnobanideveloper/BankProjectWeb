package com.eastnets.validation.strategies;


import com.eastnets.exceptions.InvalidFieldException;
import com.eastnets.model.Customer;
import com.eastnets.util.StringUtils;
import com.eastnets.validation.ValidationStrategy;

public class PhoneNumberValidation implements ValidationStrategy<Customer> {

    @Override
    public void validate(Customer customer) {
        if(StringUtils.isBlankOrNull(customer.getPhoneNumber())) {
            throw new InvalidFieldException("Phone number must not be empty");
        } if(customer.getPhoneNumber().length() < 10) {
            throw new InvalidFieldException("Phone number must be at least 10 digits");
        }
    }
}

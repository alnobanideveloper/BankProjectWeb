package com.eastnets.validation.strategies;


import com.eastnets.constants.BusinessConstants;
import com.eastnets.exceptions.InvalidFieldException;
import com.eastnets.model.Account;
import com.eastnets.validation.ValidationStrategy;

public class validateBalance implements ValidationStrategy<Account> {

    @Override
    public void validate(Account account) {
        if(account.getBalance()<= BusinessConstants.MIN_BALANCE){
            throw new InvalidFieldException("Balance must be greater than 200");
        }
    }
}

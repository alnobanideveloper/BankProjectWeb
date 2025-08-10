package com.eastnets.util;

import com.eastnets.model.Customer;
import com.eastnets.validation.ValidationStrategy;
import com.eastnets.validation.Validator;
import com.eastnets.validation.strategies.*;
import org.xml.sax.SAXException;

import javax.xml.transform.Source;
import java.io.IOException;
import java.util.List;

public class validatorUtil {
    private validatorUtil() {}

    public static void validateCustomer(Customer customer)  {

        List<ValidationStrategy> strategies = List.of(
                new EmailValidation(),
                new PasswordValidation(),
                new nameValidation(),
                new PhoneNumberValidation(),
                new NationalIDValidation()
        );

        Validator validator = new Validator(strategies);
        validator.validate(validator);

    }

}

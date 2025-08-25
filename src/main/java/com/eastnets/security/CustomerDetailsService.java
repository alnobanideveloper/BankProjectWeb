package com.eastnets.security;

import com.eastnets.model.Customer;
import com.eastnets.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerDetailsService implements UserDetailsService {
    @Autowired
    private CustomerService customerService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<Customer> customer = customerService.getCustomerByEmail(username);
       if(customer.isEmpty())
           throw new UsernameNotFoundException("invalid email");

        return new CustomerUserDetails(customer.get());
    }
}

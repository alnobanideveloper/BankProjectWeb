package com.eastnets.security;

import com.eastnets.model.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomerUserDetails implements UserDetails {
    private final Customer customer;
    public CustomerUserDetails(Customer customer){
        System.out.println("from userDetails" + customer);
        this.customer = customer;
    }

    public Customer getCustomer(){
        return customer;
    }

    public String getEmail(){
        return customer.getEmail();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return customer.getPassword();
    }

    @Override
    public String getUsername() {
        return customer.getName();
    }
}

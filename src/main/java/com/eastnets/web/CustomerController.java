package com.eastnets.web;

import com.eastnets.model.Customer;
import com.eastnets.security.CustomerUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/customer")

public class CustomerController {

    @GetMapping
    public ResponseEntity<Customer> getCustomer() {
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerUserDetails.getCustomer();
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}


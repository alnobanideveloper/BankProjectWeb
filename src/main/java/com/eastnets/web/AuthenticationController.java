package com.eastnets.web;

import com.eastnets.model.Branch;
import com.eastnets.model.Customer;
import com.eastnets.services.AuthService;
import com.eastnets.services.BranchService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController

public class AuthenticationController {
    private final AuthService authService;
    private final BranchService branchService;

    public AuthenticationController(AuthService authService , BranchService branchService){
     this.authService = authService;
     this.branchService = branchService;
    }

//    @PostMapping("/login")
//    public ResponseEntity<Customer> login(HttpSession session , @RequestParam("email") String email, @RequestParam("password") String password){
//        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
//        bcrypt.encode("saad");
//        Customer customer = authService.signIn(email, password); //if its invalid it will be cought in the exception handler
//        session.setAttribute("customer" , customer);
//
//        return new ResponseEntity<>(customer , HttpStatus.OK);
//    }

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer dto) {

        Branch branch = branchService.getBranchByNumber(dto.getBranch().getNumber()); // throws exception if invalid

        Customer customer = new Customer.Builder()
                .setAddress(dto.getAddress())
                .setName(dto.getName())
                .setPhoneNumber(dto.getPhoneNumber())
                .setEmail(dto.getEmail())
                .setPassword(dto.getPassword())
                .setNationalId(dto.getNationalId())
                .setBranch(branch)
                .build();

        Customer newCustomer = authService.registerCustomer(customer); // throws exception if invalid

        return new ResponseEntity<>(newCustomer,HttpStatus.OK );
    }
}

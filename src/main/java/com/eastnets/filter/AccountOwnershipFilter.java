//package com.eastnets.filter;
//
//import com.eastnets.config.AppConfig;
//import com.eastnets.model.Account;
//import com.eastnets.model.Customer;
//import com.eastnets.security.CustomerUserDetails;
//import com.eastnets.services.AccountService;
//import com.eastnets.util.RequestValidator;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.security.Principal;
//
//@Component
//public class AccountOwnershipFilter extends OncePerRequestFilter {
//    private final AccountService accountService;
//
//    public AccountOwnershipFilter(AccountService accountService) {
//        this.accountService = accountService;
//    }
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
//        // Apply filter only for /accounts/** URLs
//
//        System.out.println(req.getRequestURI());
//        System.out.println(req.getRequestURI().startsWith("/BankWebProject_war_exploded/accounts/"));
//
//        if (!req.getRequestURI().startsWith("/BankWebProject_war_exploded/accounts/")) {
//            filterChain.doFilter(req, res);
//            return;
//        }
//        if(hasMissingParams(req, res)) return;
//
//        CustomerUserDetails customerDetails =(CustomerUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Customer customer = customerDetails.getCustomer();
//
//        String[] parts = req.getRequestURL().split("/");
//        Account account = accountService.getAccountByNumber(accountNumber);
//
//
//        //if the account number belongs to the customer
//        if (customerOwnsAccount(account, customer))
//            filterChain.doFilter(req, res);
//            //if the account does not belong to the customer
//        else
//            sendError(res, HttpServletResponse.SC_NOT_FOUND, "you dont have access to this account");
//    }
//
//    private static boolean hasMissingParams(HttpServletRequest req, HttpServletResponse res) throws IOException {
//        return !RequestValidator.checkRequiredParams(req, res, "accountNumber");
//    }
//
//    private static boolean customerOwnsAccount(Account account, Customer customer) {
//        return account.getNationalID().equals(customer.getNationalId());
//    }
//
//    private  void sendError(HttpServletResponse res , int statusCode , String massege) throws IOException{
//        res.setStatus(statusCode);
//        res.getWriter().write(massege);
//    }
//
//}

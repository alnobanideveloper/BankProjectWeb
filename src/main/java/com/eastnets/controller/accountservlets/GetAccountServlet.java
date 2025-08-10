package com.eastnets.controller.accountservlets;

import com.eastnets.dao.AccountDAO;
import com.eastnets.dao.CustomerDAO;
import com.eastnets.dao.TransactionDAO;
import com.eastnets.model.Account;
import com.eastnets.model.Customer;
import com.eastnets.services.AccountService;
import com.eastnets.services.CustomerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

//will be protected using authFilter
//this servlets job is to get the accounts of a specific customer
@WebServlet("/accounts")
public class GetAccountServlet extends HttpServlet {
    private final AccountService accountService = new AccountService(new AccountDAO() , new TransactionDAO());

    //do get because client is not uploading something to database ,fetching from database
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Customer customer = getCustomerFromSession(req);
        PrintWriter out = res.getWriter();

        //return all his accounts
        List<Account> accounts = accountService.getAllAccounts(customer.getNationalId());
        res.setStatus(HttpServletResponse.SC_OK);
        out.println(accounts);
    }

    private Customer getCustomerFromSession(HttpServletRequest req){
        HttpSession session = req.getSession();
        Customer customer =(Customer) session.getAttribute("customer");
        return customer;
    }

}

package com.eastnets.controller.accountservlets;

import com.eastnets.dao.AccountDAO;
import com.eastnets.dao.TransactionDAO;
import com.eastnets.exceptions.InvalidFieldException;
import com.eastnets.model.Account;
import com.eastnets.model.Customer;
import com.eastnets.services.AccountService;
import com.eastnets.util.RequestValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/accounts/create")
public class AddAccountServlet extends HttpServlet {
    private final AccountService accountService = new AccountService(new AccountDAO() , new TransactionDAO());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(hasInvalidParams(req , resp)) return;
        Customer customer = getCustomerFromSession(req);

        double balance = Double.parseDouble(req.getParameter("balance").trim());
        String accountType = req.getParameter("type").trim();

        Account account = new Account(accountType , balance,customer.getNationalId());
        try{
            handleAccountCreation(resp , account);
        }catch(InvalidFieldException ex){ //service validation exceptions
           sendResponse(resp , HttpServletResponse.SC_BAD_REQUEST , ex.getMessage());
        }
    }

    private void handleAccountCreation(HttpServletResponse resp, Account account) throws IOException {
        Optional<Account> newAccount = accountService.createAccount(account);
        if (newAccount.isPresent())
           sendResponse(resp , HttpServletResponse.SC_OK , "account successfully created");
    }

    private static void sendResponse(HttpServletResponse res ,int resCode, String massege) throws IOException {
        res.setStatus(resCode);
        res.getWriter().println(massege);
    }


    private Customer getCustomerFromSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        return customer;
    }

    private boolean hasInvalidParams(HttpServletRequest req , HttpServletResponse res) throws IOException {
        if(!RequestValidator.checkRequiredParams(req , res , "balance" , "type")) return true; //if one of the parameters are missing
        return !RequestValidator.checkDoubleParams(req, res, "balance"); //if the double parameter is string
    }
}

package com.eastnets.controller.servicesservlets;

import com.eastnets.dao.AccountDAO;
import com.eastnets.dao.TransactionDAO;
import com.eastnets.exceptions.InvalidFieldException;
import com.eastnets.model.Account;
import com.eastnets.services.AccountService;
import com.eastnets.util.RequestValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/transactions/deposit")
public class DepositServlet extends HttpServlet {
    private final AccountService accountService = new AccountService(new AccountDAO() , new TransactionDAO());


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        if(hasInvalidParams(req , res ))
            return;

        int accountNumber = Integer.parseInt(req.getParameter("acc"));
        double amount = Double.parseDouble(req.getParameter("amount"));

        Account account = accountService.getAccountByNumber(accountNumber).get();
        performDeposit(account, amount, res);   //I am sure that this is a valid account because there is a filter that checks that

    }

    private void performDeposit (Account account, double amount, HttpServletResponse res) throws IOException {
        try{
            double remainingBalance =  accountService.deposit(account , amount);
            sendResponse(res , HttpServletResponse.SC_OK , "successfully deposited , remaining balance is : " + remainingBalance);
        }catch(InvalidFieldException ex){
            sendResponse(res , HttpServletResponse.SC_BAD_REQUEST , ex.getMessage());
        }
    }

    private boolean hasInvalidParams(HttpServletRequest req , HttpServletResponse res) throws IOException {
        if(!RequestValidator.checkRequiredParams(req , res,"acc" , "amount")) return true;
        if(!RequestValidator.checkIntegerParams(req , res , "acc")) return true;
        return !RequestValidator.checkDoubleParams(req ,res , "amount");
    }

    private static void sendResponse(HttpServletResponse res ,int resCode, String massege) throws IOException {
        res.setStatus(resCode);
        res.getWriter().println(massege);
    }

}

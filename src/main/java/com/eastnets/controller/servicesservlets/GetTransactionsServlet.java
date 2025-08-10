package com.eastnets.controller.servicesservlets;

import com.eastnets.dao.AccountDAO;
import com.eastnets.dao.TransactionDAO;
import com.eastnets.model.Account;
import com.eastnets.model.Transaction;
import com.eastnets.services.AccountService;
import com.eastnets.services.TransactionService;
import com.eastnets.util.RequestValidator;
import com.sun.net.httpserver.HttpsServer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/transactions")
public class GetTransactionsServlet extends HttpServlet {
    private final TransactionService transactionService = new TransactionService(new TransactionDAO());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if(hasInvalidParams(req , res )) return;
        int accountNumber = Integer.parseInt(req.getParameter("acc"));

        List<Transaction> transactions =  transactionService.getAllTransactions(accountNumber);

        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().println(transactions);
    }


    private static boolean hasInvalidParams(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if( !RequestValidator.checkRequiredParams(req, resp, "acc")) return true;
        return !RequestValidator.checkIntegerParams(req , resp , "acc");
    }
}

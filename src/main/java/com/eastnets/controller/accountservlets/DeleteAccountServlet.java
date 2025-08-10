package com.eastnets.controller.accountservlets;

import com.eastnets.dao.AccountDAO;
import com.eastnets.dao.TransactionDAO;
import com.eastnets.model.Account;
import com.eastnets.services.AccountService;
import com.eastnets.util.RequestValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/accounts/delete")
public class DeleteAccountServlet extends HttpServlet {
    private final AccountService accountService = new AccountService(new AccountDAO() , new TransactionDAO());

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if(hasInvalidParams(req , res )) return;
        int accountNumber = Integer.parseInt(req.getParameter("acc").trim());
        Account account = accountService.getAccountByNumber(accountNumber).get();

        int deletedAccounts = accountService.deleteAccount(account);

        if (deletedAccounts == 1)
            sendResponse(res , 200 , "account deleted successfully");
        else
            sendResponse(res , HttpServletResponse.SC_INTERNAL_SERVER_ERROR , "failed deleting account , please try again");
    }

    private static void sendResponse(HttpServletResponse res ,int resCode, String massege) throws IOException {
        res.setStatus(resCode);
        res.getWriter().println(massege);
    }

    private static boolean hasInvalidParams(HttpServletRequest req , HttpServletResponse res) throws IOException {
        if(!RequestValidator.checkRequiredParams(req , res , "acc")) return true; //if one of the parameters is missing
        return !RequestValidator.checkIntegerParams(req, res, "acc"); // check if it's not integer
        //if checkIntparams is true -> meaning that it is an integer , I will return false so it is valid
    }
}

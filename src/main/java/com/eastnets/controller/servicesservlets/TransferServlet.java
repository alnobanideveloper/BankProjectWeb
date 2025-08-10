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

@WebServlet("/transactions/transfer")
public class TransferServlet extends HttpServlet {
    private final AccountService accountService = new AccountService(new AccountDAO() , new TransactionDAO());


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(hasInvalidParams(req, resp)) return;

        PrintWriter out = resp.getWriter();
        Result params = getParams(req);

        Account fromAccount = accountService.getAccountByNumber(params.acc()).get(); //there will be an account becuase it is filtered
        Optional<Account> toAccount = accountService.getAccountByNumber(params.toAcc());
        
        if(toAccount.isPresent())
            performTransfer(resp, params.amount(), fromAccount, toAccount, out);
        else
            sendResponse(resp , HttpServletResponse.SC_BAD_REQUEST ,"not a valid account number" );

    }

    private static Result getParams(HttpServletRequest req) {
        Double amount = Double.parseDouble(req.getParameter("amount"));
        int acc = Integer.parseInt(req.getParameter("acc"));
        int toAcc = Integer.parseInt(req.getParameter("toAcc"));

        return new Result(amount, acc, toAcc);
    }

    private record Result(Double amount, int acc, int toAcc) {
    }

    private void performTransfer(HttpServletResponse resp, Double amount, Account fromAccount, Optional<Account> toAccount, PrintWriter out)throws IOException {
        try{
            Double newBalance = accountService.transfer(amount, fromAccount, toAccount.get());
            sendResponse(resp , HttpServletResponse.SC_OK , "successful transfer, the remaining balance is: " + newBalance);
        }catch(InvalidFieldException ex){
           sendResponse(resp , HttpServletResponse.SC_BAD_REQUEST , ex.getMessage());
        }
    }



    private static boolean hasInvalidParams(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       if(!RequestValidator.checkRequiredParams(req, resp, "amount", "acc", "toAcc")) return true;
       if(!RequestValidator.checkIntegerParams(req , resp , "acc" , "toAcc")) return true;
       return !RequestValidator.checkDoubleParams(req , resp , "amount");
    }

    private static void sendResponse(HttpServletResponse res ,int resCode, String massege) throws IOException {
        res.setStatus(resCode);
        res.getWriter().println(massege);
    }
}

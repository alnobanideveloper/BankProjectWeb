package com.eastnets.filter;

import com.eastnets.dao.AccountDAO;
import com.eastnets.dao.TransactionDAO;
import com.eastnets.model.Account;
import com.eastnets.model.Customer;
import com.eastnets.services.AccountService;
import com.eastnets.util.RequestValidator;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebFilter(urlPatterns = {"/transactions/*"})
public class AccountOwnershipFilter implements Filter {
    private AccountService accountService;

    @Override
    public void init(FilterConfig filterConfig) {
        AccountDAO accountDAO = new AccountDAO();
        TransactionDAO transactionDAO = new TransactionDAO();
        this.accountService = new AccountService(accountDAO, transactionDAO);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();

        if(hasMissingParams(req, res)) return;

        Customer customer = (Customer) session.getAttribute("customer");
        int accountNumber = Integer.parseInt(req.getParameter("acc"));
        Optional<Account> account = accountService.getAccountByNumber(accountNumber);

        if (account.isPresent()) {
            //if the account number belongs to the customer
            if (customerOwnsAccount(account.get(), customer))
                filterChain.doFilter(servletRequest, servletResponse);
            //if the account does not belong to the customer
            else
                sendError(res, HttpServletResponse.SC_NOT_FOUND, "you dont have access to this account");
        //if it's not an existing account
        } else
            sendError(res, HttpServletResponse.SC_NOT_FOUND, "Invalid account number.");
    }

    private static boolean hasMissingParams(HttpServletRequest req, HttpServletResponse res) throws IOException {
        return !RequestValidator.checkRequiredParams(req, res, "acc");
    }

    private static boolean customerOwnsAccount(Account account, Customer customer) {
        return account.getNationalID().equals(customer.getNationalId());
    }

    private  void sendError(HttpServletResponse res , int statusCode , String massege) throws IOException{
        res.setStatus(statusCode);
        res.getWriter().write(massege);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

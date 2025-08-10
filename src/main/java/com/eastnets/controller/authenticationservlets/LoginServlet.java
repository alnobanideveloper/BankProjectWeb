package com.eastnets.controller.authenticationservlets;

import com.eastnets.dao.CustomerDAO;
import com.eastnets.model.Customer;
import com.eastnets.services.AuthService;
import com.eastnets.util.RequestValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    AuthService authService = new AuthService(new CustomerDAO());


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if(hasMissingParams(req, res)) return;

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        Optional<Customer> loggedInCustomer = authService.signIn(email, password);
        if (loggedInCustomer.isPresent())
            handleSuccessfulLogin(req, res, loggedInCustomer);
        //if not valid
        else
            sendResponse(res , HttpServletResponse.SC_UNAUTHORIZED , "invalid credentials");
    }

    //if its valid credentials, add the customer to the session to save him, and send a seccess massege
    private void handleSuccessfulLogin(HttpServletRequest req, HttpServletResponse res, Optional<Customer> loggedInCustomer) throws IOException {
        addCustomerToSession(req, loggedInCustomer.get());
        sendResponse(res , HttpServletResponse.SC_OK , "succesfully accessed");
    }

    private static void sendResponse(HttpServletResponse res ,int resCode, String massege) throws IOException {
        res.setStatus(resCode);
        res.getWriter().println(massege);
    }


    private static boolean hasMissingParams(HttpServletRequest req, HttpServletResponse res) throws IOException {
        return !RequestValidator.checkRequiredParams(req, res, "email", "password");
    }

    private void addCustomerToSession(HttpServletRequest req, Customer customer) {
        req.getSession().setAttribute("customer", customer);

    }
}


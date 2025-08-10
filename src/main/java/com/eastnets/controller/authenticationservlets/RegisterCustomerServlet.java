package com.eastnets.controller.authenticationservlets;

import com.eastnets.dao.BranchDAO;
import com.eastnets.dao.CustomerDAO;
import com.eastnets.exceptions.InvalidFieldException;
import com.eastnets.model.Branch;
import com.eastnets.model.Customer;
import com.eastnets.services.AuthService;
import com.eastnets.services.BranchService;
import com.eastnets.util.RequestValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/auth/register")
public class RegisterCustomerServlet extends HttpServlet {
    private final BranchService branchService = new BranchService(new BranchDAO());
    private final AuthService authService = new AuthService(new CustomerDAO());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (hasInvalidParams(req, resp)) return;
        Result params = getParams(req);

        Branch branch = branchService.getBranchByNumber(params.branchNumber()).get();

        Customer customer = new Customer.Builder()
                .setAddress(params.address())
                .setName(params.name())
                .setPhoneNumber(params.phoneNumber())
                .setEmail(params.email())
                .setPassword(params.password())
                .setNationalId(params.nationalId())
                .setBranch(branch)
                .build();

        try {
            handleCustomerRegistration(resp, customer);
        } catch (InvalidFieldException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(ex.getMessage());
        }
    }


    private static Result getParams(HttpServletRequest req) {
        String address = req.getParameter("address").trim();
        String name = req.getParameter("name").trim();
        String phoneNumber = req.getParameter("phoneNumber").trim();
        String nationalId = req.getParameter("nationalId").trim();
        int branchNumber = Integer.parseInt(req.getParameter("branchNumber").trim());
        String password = req.getParameter("password").trim();
        String email = req.getParameter("email").trim();
        Result result = new Result(address, name, phoneNumber, nationalId, password, email , branchNumber);
        return result;
    }

    private record Result(String address, String name, String phoneNumber, String nationalId, String password, String email , int branchNumber) { }

    private void handleCustomerRegistration(HttpServletResponse resp, Customer customer) throws IOException {
        Optional<Customer> newCustomer = authService.registerCustomer(customer);

        if (newCustomer.isPresent()) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("Customer successfully registered");
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Failed to register customer");
        }
    }


    //better to implement strategy pattern for validation
    private boolean hasInvalidParams(HttpServletRequest req, HttpServletResponse res) throws IOException {
        // Required params check
        if (!RequestValidator.checkRequiredParams(req, res, "address", "name", "phoneNumber", "nationalId", "branchNumber", "password", "email"))
            return true;

        // Check integer parameters
        if (!RequestValidator.checkIntegerParams(req, res, "branchNumber"))
            return true;
        //to check if the giving brannch number exists
        Optional<Branch> branch = branchService.getBranchByNumber(getParams(req).branchNumber);
        if (branch.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write("Invalid branch number: Branch does not exist");
            return true;
        }
        return false;

    }

}

package com.eastnets.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/accounts/*" , "/transactions/*" , "/transactions"})
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    //check if the user is loggged in
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        if (isNotAuthenticated(req))
            sendUnAuthorized(res);
        else
            filterChain.doFilter(req, res);

    }

    private static void sendUnAuthorized(HttpServletResponse res) throws IOException {
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.getWriter().write("you must be logged in to access");
    }

    private static boolean isNotAuthenticated(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session == null || session.getAttribute("customer") == null;
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}







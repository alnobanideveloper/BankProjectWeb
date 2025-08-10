package com.eastnets.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RequestValidator {

    public static boolean checkRequiredParams(HttpServletRequest request, HttpServletResponse response, String... params) throws IOException {
        for (String param : params) {
            String value = request.getParameter(param);
            if (value == null || value.isBlank()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Missing required parameter: " + param);
                return false;
            }
        }
        return true;
    }

    public static boolean checkDoubleParams(HttpServletRequest request, HttpServletResponse response, String... params) throws IOException {
        for (String param : params) {
            try {
                double value = Double.parseDouble(request.getParameter(param).trim());
            }catch(NumberFormatException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(param + "should be a double number");
                return false;
            }
        }
        return true;
    }

    public static boolean checkIntegerParams(HttpServletRequest request, HttpServletResponse response, String... params) throws IOException {
        for (String param : params) {
            try {
                int value = Integer.parseInt(request.getParameter(param).trim());
            }catch(NumberFormatException ex) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(param + "should be a integer number");
                return false;
            }
        }
        return true;
    }
}

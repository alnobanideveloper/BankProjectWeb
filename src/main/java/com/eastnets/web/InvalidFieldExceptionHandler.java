package com.eastnets.web;


import com.eastnets.exceptions.InvalidFieldException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class InvalidFieldExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<String> handleIllegalArgument(InvalidFieldException ex) {
        return new ResponseEntity<>("error" + ex.getMessage() , HttpStatus.BAD_REQUEST);
    }


}


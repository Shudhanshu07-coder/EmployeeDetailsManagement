package com.example.employee.EmployeeDetails.exception;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    MessageSource messageSource;

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<List<String>> handleConstraintViolation(ConstraintViolationException ex,
            WebRequest request) {
        List<String> details = ex.getConstraintViolations().parallelStream().map(e -> e.getMessage())
                .collect(Collectors.toList());

        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public final ResponseEntity<String> handleResourceNotFound(ResourceNotFound ex, WebRequest request) {

        String msg = messageSource.getMessage("error.notfound", null, request.getLocale());
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExists.class)
    public final ResponseEntity<String> handleResourceAlreadyExists(ResourceAlreadyExists ex, WebRequest request) {
        String msg = messageSource.getMessage("error.alreadyExist", null, request.getLocale());
        return new ResponseEntity<>(msg, HttpStatus.CONFLICT);
    }
}

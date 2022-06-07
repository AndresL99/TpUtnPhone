package com.utnphones.tputnphones.exception.handledException;

import javax.validation.ConstraintViolationException;

import com.utnphones.tputnphones.exception.ClientExistException;
import com.utnphones.tputnphones.exception.ClientNotExistException;
import com.utnphones.tputnphones.exception.UserExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handledConstraintViolation(ConstraintViolationException exception, WebRequest webRequest)
    {
        List<String> errors = new ArrayList<>();
        for(ConstraintViolation constraintViolation: exception.getConstraintViolations())
        {
            errors.add(constraintViolation.getRootBeanClass().getName()+""+constraintViolation.getMessage());
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,exception.getLocalizedMessage(),errors);
        return new ResponseEntity<Object>(apiError,new HttpHeaders(),apiError.getHttpStatus());
    }

    @ExceptionHandler({ClientExistException.class})
    public ResponseEntity<Object>clientAlreadyExist(ClientExistException c)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(c.getMessage());
    }

    @ExceptionHandler({ClientNotExistException.class})
    public ResponseEntity<Object>clientNotExist(ClientNotExistException c)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(c.getMessage());
    }

    @ExceptionHandler({UserExistException.class})
    public ResponseEntity<Object>userAlreadyExist(UserExistException c)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(c.getMessage());
    }
}

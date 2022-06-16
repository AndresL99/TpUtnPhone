package com.utnphones.tputnphones.exception.handledException;

import javax.validation.ConstraintViolationException;

import com.utnphones.tputnphones.exception.BillExistException;
import com.utnphones.tputnphones.exception.BillNotExistException;
import com.utnphones.tputnphones.exception.CallExistException;
import com.utnphones.tputnphones.exception.CallNotExistException;
import com.utnphones.tputnphones.exception.ClientExistException;
import com.utnphones.tputnphones.exception.ClientNotExistException;
import com.utnphones.tputnphones.exception.PhoneLineExistException;
import com.utnphones.tputnphones.exception.PhoneLineNotExistException;
import com.utnphones.tputnphones.exception.TariffExistException;
import com.utnphones.tputnphones.exception.TariffNotExistException;
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

    @ExceptionHandler({BillExistException.class})
    public ResponseEntity<Object>billAlreadyExist(BillExistException b)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(b.getMessage());
    }

    @ExceptionHandler({BillNotExistException.class})
    public ResponseEntity<Object>billNotExist(BillNotExistException b)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(b.getMessage());
    }

    @ExceptionHandler({PhoneLineExistException.class})
    public ResponseEntity<Object>phoneLineAlreadyExist(PhoneLineExistException p)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(p.getMessage());
    }

    @ExceptionHandler({PhoneLineNotExistException.class})
    public ResponseEntity<Object>phoneLineNotExist(PhoneLineNotExistException p)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(p.getMessage());
    }

    @ExceptionHandler({TariffExistException.class})
    public ResponseEntity<Object>tariffAlreadyExist(TariffExistException t)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(t.getMessage());
    }

    @ExceptionHandler({TariffNotExistException.class})
    public ResponseEntity<Object>tariffNotExist(TariffNotExistException t)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(t.getMessage());
    }

    @ExceptionHandler({CallExistException.class})
    public ResponseEntity<Object>callAlreadyExist(CallExistException c)
    {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(c.getMessage());
    }

    @ExceptionHandler({CallNotExistException.class})
    public ResponseEntity<Object>callNotExist(CallNotExistException c)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(c.getMessage());
    }


}

package com.utnphones.tputnphones.exception.handledException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError
{
    private HttpStatus httpStatus;
    private String message;
    private List<String>errors;
}

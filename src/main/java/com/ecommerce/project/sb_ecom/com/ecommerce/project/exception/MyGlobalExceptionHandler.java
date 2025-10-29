package com.ecommerce.project.sb_ecom.com.ecommerce.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class MyGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> myMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String, String> response = new HashMap<>();
        exception.getBindingResult()
                .getAllErrors()
                .forEach(err ->
                {
                    String fieldName = ((FieldError) err).getField();
                    String message = err.getDefaultMessage();
                    response.put(fieldName, message);
                });
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<String> myResourceNotFoundException(ResourceNotFoundException exception){
            String message = exception.getMessage();
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<String> myAPIException(APIException exception){
        String message = exception.getMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


}

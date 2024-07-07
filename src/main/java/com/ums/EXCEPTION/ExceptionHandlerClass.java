package com.ums.EXCEPTION;


import com.ums.PAYLOAD.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice // the exception from the project automatically comes here and handle by the methods  // act as a global exception handler
public class ExceptionHandlerClass {
    @ExceptionHandler( ResourceNotFound.class)// it only handle the Resourcesnotfound exception in that project
    public ResponseEntity<ErrorDetails > resourceNotFoundException(Exception exception, WebRequest webRequest){
        ErrorDetails error=new ErrorDetails(exception.getMessage(),new Date(),webRequest.getDescription(false)); //give message and date of the exception //PAYLOAD
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler( Exception.class)// handle other exception in that project except the ResourceNotFound
    public ResponseEntity<ErrorDetails > globalExceptionHandler(ResourceNotFound exception, WebRequest webRequest){
        ErrorDetails error=new ErrorDetails(exception.getMessage(),new Date(),webRequest.getDescription(false)); //give message and date of the exception //PAYLOAD
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
}
}

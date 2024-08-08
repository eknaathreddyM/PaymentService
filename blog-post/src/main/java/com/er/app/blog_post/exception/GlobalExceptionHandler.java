package com.er.app.blog_post.exception;

import com.er.app.blog_post.Dto.ErrorDetails;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.modelmapper.internal.bytebuddy.asm.MemberSubstitution;
import org.modelmapper.internal.bytebuddy.implementation.bind.annotation.FieldValue;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    //ResourceNotFound Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundException(ResourceNotFoundException exception,
                                                                  WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false)
        , HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    //Bad request error to handle wrong request
    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorDetails> blogApiException(BlogApiException exception, WebRequest request){
        ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }


    //global exception to handle exceptions globally
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> globalException(Exception exception, WebRequest request){
        ErrorDetails errorDetails=new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //validation response error method overriding
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> result=new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(e->{
           String error= ((FieldError)e).getField();
           String message=e.getDefaultMessage();
           result.put(error,message);
        });

        return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
    }
}

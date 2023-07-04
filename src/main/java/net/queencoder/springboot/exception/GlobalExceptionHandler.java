package net.queencoder.springboot.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
        		new Date(), 
        		ex.getMessage(), 
        		request.getDescription(false),
        		HttpStatus.NOT_FOUND.value()
        		);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
//        ErrorDetails errorDetails = new ErrorDetails(
//        		new Date(), 
//        		ex.getMessage(), 
//        		request.getDescription(false),
//        		HttpStatus.INTERNAL_SERVER_ERROR.value()
//        		);
//        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> customMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
        		new Date(), 
        		ex.getMessage(), 
        		request.getDescription(false),
        		HttpStatus.BAD_REQUEST.value()
        		);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingParams(MissingServletRequestParameterException ex, WebRequest request) {
    	ErrorDetails errorDetails = new ErrorDetails(
        		new Date(), 
        		ex.getMessage(), 
        		request.getDescription(false),
        		HttpStatus.BAD_REQUEST.value()
        		);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}


package net.queencoder.springboot.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomMethodArgumentNotValidException extends Exception{
	private static final long serialVersionUID = 1L;

    public CustomMethodArgumentNotValidException(String message){
        super(message);
    }

}

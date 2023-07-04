package net.queencoder.springboot.exception;

public class MethodArgumentNotValidException extends Exception{
	private static final long serialVersionUID = 1L;

    public MethodArgumentNotValidException(String message){
        super(message);
    }
}

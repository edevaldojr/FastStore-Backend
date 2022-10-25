package br.com.faststore.lopestyle.services.Exceptions;


public class InsufficientAmountException extends RuntimeException{
    
    public InsufficientAmountException(String msg) {
        super(msg);
    }

    public InsufficientAmountException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
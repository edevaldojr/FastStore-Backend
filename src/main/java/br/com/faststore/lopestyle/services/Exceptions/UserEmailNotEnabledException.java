package br.com.faststore.lopestyle.services.Exceptions;

public class UserEmailNotEnabledException extends RuntimeException {
    
    public UserEmailNotEnabledException(String msg) {
        super(msg);
    }

    public UserEmailNotEnabledException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

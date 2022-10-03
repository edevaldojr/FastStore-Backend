package br.com.faststore.lopestyle.services.Exceptions;

public class ObjectAlreadyExistsException extends RuntimeException {
    
    public ObjectAlreadyExistsException(String msg) {
        super(msg);
    }

    public ObjectAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

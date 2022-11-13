package br.com.faststore.lopestyle.services.Exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.faststore.lopestyle.services.Exceptions.AuthorizationException;
import br.com.faststore.lopestyle.services.Exceptions.FileException;
import br.com.faststore.lopestyle.services.Exceptions.InsufficientAmountException;
import br.com.faststore.lopestyle.services.Exceptions.ObjectAlreadyExistsException;
import br.com.faststore.lopestyle.services.Exceptions.ObjectNotFoundException;
import br.com.faststore.lopestyle.services.Exceptions.UserEmailNotEnabledException;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
    public final ResponseEntity<?> handleAllExceptions(ObjectNotFoundException exception, WebRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationException.class)
    public final ResponseEntity<?> handleAllExceptions(AuthorizationException exception, WebRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(FileException.class)
    public final ResponseEntity<?> handleAllExceptions(FileException exception, WebRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectAlreadyExistsException.class)
    public final ResponseEntity<?> handleAllExceptions(ObjectAlreadyExistsException exception, WebRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserEmailNotEnabledException.class)
    public final ResponseEntity<?> handleAllExceptions(UserEmailNotEnabledException exception, WebRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientAmountException.class)
    public final ResponseEntity<?> handleAllExceptions(InsufficientAmountException exception, WebRequest request) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}   

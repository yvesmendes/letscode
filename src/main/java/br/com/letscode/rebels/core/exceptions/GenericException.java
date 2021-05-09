package br.com.letscode.rebels.core.exceptions;


import org.springframework.http.HttpStatus;

public class GenericException extends BusinessException{

    public GenericException(String message) {
        super(message, HttpStatus.NOT_FOUND.ordinal());
    }

    public GenericException(String message, int statusCode) {
        super(message, statusCode);
    }
}

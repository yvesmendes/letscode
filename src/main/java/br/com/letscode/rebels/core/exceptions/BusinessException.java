package br.com.letscode.rebels.core.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BusinessException extends RuntimeException{
    private String message;
    private int statusCode;

    public BusinessException(String message, int statusCode){
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }
}

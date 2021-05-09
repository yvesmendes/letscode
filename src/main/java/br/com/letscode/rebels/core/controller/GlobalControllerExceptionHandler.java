package br.com.letscode.rebels.core.controller;

import br.com.letscode.rebels.core.dto.DefaultError;
import br.com.letscode.rebels.core.exceptions.GenericException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultError> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        logger.error("Erro na validação dos dados {}",ex.getMessage());
        return new ResponseEntity<>(DefaultError.builder().errorMessage("Erro na validação dos dados").statusCode(HttpStatus.BAD_REQUEST.ordinal()).listOfErros(errors).build(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<DefaultError> handleNotFoundExceptions(
            GenericException ex) {
        logger.error("Entidade não encontrada {}",ex.getMessage());
        return new ResponseEntity<>(DefaultError.builder().errorMessage(ex.getMessage()).statusCode(ex.getStatusCode()).listOfErros(null).build(), ex.getStatusCode() == HttpStatus.NOT_FOUND.ordinal() ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST);
    }
}
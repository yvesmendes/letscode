package br.com.letscode.rebels.core.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class DefaultError {
    private int statusCode;
    private String errorMessage;
    private Map<String,String> listOfErros;
}

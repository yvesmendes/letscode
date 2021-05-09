package br.com.letscode.rebels.person.domain.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class LocalizationDTO {

    private double latitude;
    private double longitude;
    @NotBlank(message = "Nome da galáxia é obrigatório")
    private String galaxyName;
}

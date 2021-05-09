package br.com.letscode.rebels.person.domain.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString(callSuper = true)
public class LocalizationWithIDDTO extends LocalizationDTO {
    @NotBlank(message = "O ID do rebelde é obrigatório")
    private String id;
}

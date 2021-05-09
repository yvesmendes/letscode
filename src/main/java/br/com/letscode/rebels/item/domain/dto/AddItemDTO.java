package br.com.letscode.rebels.item.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AddItemDTO {

    @NotBlank(message = "O campo nome é obrigatório")
    private String name;
    @Min(value = 1, message = "O campo points deve ser maior que 0")
    private int points;
}

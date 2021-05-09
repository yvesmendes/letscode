package br.com.letscode.rebels.item.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class ItemUpdateDTO {
    @Min(value = 1)
    private Long id;
    @NotBlank(message = "")
    private String name;
    @Min(value = 1)
    private int points;
}

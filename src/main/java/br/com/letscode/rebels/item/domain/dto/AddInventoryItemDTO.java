package br.com.letscode.rebels.item.domain.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = {"id"})
public class AddInventoryItemDTO {

    @Min(value = 1, message = "O ID do item deve ser maior que 0")
    private Long id;

    @Min(value = 1, message = "A quantidade do item deve ser maior que 0")
    private int quantity;
}

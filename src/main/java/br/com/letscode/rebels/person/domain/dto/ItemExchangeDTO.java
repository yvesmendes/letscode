package br.com.letscode.rebels.person.domain.dto;

import lombok.*;

import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemExchangeDTO {
    @Min(value = 1,message = "O ID do item deve ser maior que 0")
    private Long itemId;
    @Min(value = 1,message = "A quantidade deve ser maior que 0")
    private int quantity;
}

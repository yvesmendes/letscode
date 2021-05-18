package br.com.letscode.rebels.person.domain.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeDTO {

    @NotBlank(message = "O ID do rebelde é obrigatório")
    private String fromPersonId;
    @NotBlank(message = "O ID do rebelde é obrigatório")
    private String toPersonId;
    @NotEmpty(message = "É necessário ter ao menos um item seu na lista")
    @Valid
    private Set<ItemExchangeDTO> fromItems;
    @NotEmpty(message = "É necessário ter ao menos um item do rebelde que você vai fazer a troca na lista")
    @Valid
    private Set<ItemExchangeDTO> toItems;
}

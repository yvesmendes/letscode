package br.com.letscode.rebels.person.domain.dto;

import br.com.letscode.rebels.item.domain.dto.AddInventoryItemDTO;
import br.com.letscode.rebels.person.domain.entity.GenderEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
public class AddPersonDTO {

    @NotBlank(message = "O nome é obrigatório")
    protected String name;
    @Min(value = 1, message = "A idade deve ser maior que 0")
    protected int age;

    protected GenderEnum gender;

    @Valid
    protected LocalizationDTO currentLocalization;

    @NotEmpty(message = "É necessário informar ao menos um item para o invetário do rebelde")
    @Valid
    protected Set<AddInventoryItemDTO> inventory;
}

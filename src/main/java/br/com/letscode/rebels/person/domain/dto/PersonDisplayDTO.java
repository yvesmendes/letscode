package br.com.letscode.rebels.person.domain.dto;

import br.com.letscode.rebels.person.domain.entity.GenderEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonDisplayDTO{

    private String id;
    protected String name;
    protected int age;
    protected GenderEnum gender;
    protected LocalizationDTO currentLocalization;
    protected List<InventoryItemDTO> inventory;
    private boolean rebel;
    private int reportCount;
}

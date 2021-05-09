package br.com.letscode.rebels.person.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InventoryItemDTO {
    private Long itemId;
    private String personId;
    private int quantity;
}

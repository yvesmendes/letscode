package br.com.letscode.rebels.item.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemDTO {
    private Long id;
    private String name;
    private int points;
}

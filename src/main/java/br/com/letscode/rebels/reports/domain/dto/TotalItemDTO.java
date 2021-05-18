package br.com.letscode.rebels.reports.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TotalItemDTO {

    private String itemName;
    private String quantity;
}

package br.com.letscode.rebels.reports.domain.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportMeanResourcesDTO {
    private List<TotalItemDTO> items;
}

package br.com.letscode.rebels.reports.domain.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportTotalDTO {
    private String totalRebels;
    private String totalTraitors;
    private int totalOfPersons;
}

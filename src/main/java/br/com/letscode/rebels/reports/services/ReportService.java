package br.com.letscode.rebels.reports.services;

import br.com.letscode.rebels.reports.domain.dto.ReportMeanResourcesDTO;
import br.com.letscode.rebels.reports.domain.dto.ReportTotalDTO;
import br.com.letscode.rebels.reports.domain.dto.ReportTotalPointsDTO;

public interface ReportService {

    /**
     * Retorna o total de traidores e rebeldes do catálogo
     * @return Um objeto com a porcentagem de traidores e rebeldes
     */
    ReportTotalDTO getTotal();

    /**
     * Recupera a média de recursos por rebelde
     * @return Um objeto com uma lista de items e a media de cada item
     */
    ReportMeanResourcesDTO getReportMean();

    /**
     * Retorna todos os pontos perdidos devido a rebeldes terem virados traidores
     * @return Um objeto com o total de pontos perdidos
     */
    ReportTotalPointsDTO getLostPoints();
}

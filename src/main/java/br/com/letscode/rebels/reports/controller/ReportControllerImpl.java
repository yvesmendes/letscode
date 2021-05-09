package br.com.letscode.rebels.reports.controller;

import br.com.letscode.rebels.reports.domain.dto.ReportMeanResourcesDTO;
import br.com.letscode.rebels.reports.domain.dto.ReportTotalDTO;
import br.com.letscode.rebels.reports.domain.dto.ReportTotalPointsDTO;
import br.com.letscode.rebels.reports.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportControllerImpl implements ReportController{

    @Autowired
    private ReportService reportService;

    @Override
    public ResponseEntity<ReportTotalDTO> getTotal() {
        return ResponseEntity.ok(reportService.getTotal());
    }

    @Override
    public ResponseEntity<ReportMeanResourcesDTO> getReportMean() {
        return ResponseEntity.ok(reportService.getReportMean());
    }

    @Override
    public ResponseEntity<ReportTotalPointsDTO> getTotalLostPoints() {
        return ResponseEntity.ok(reportService.getLostPoints());
    }
}

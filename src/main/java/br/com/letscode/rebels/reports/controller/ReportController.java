package br.com.letscode.rebels.reports.controller;

import br.com.letscode.rebels.reports.domain.dto.ReportMeanResourcesDTO;
import br.com.letscode.rebels.reports.domain.dto.ReportTotalDTO;
import br.com.letscode.rebels.reports.domain.dto.ReportTotalPointsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/reports")
@Tag(name = "Report API",description = "API de relatório dos Rebeldes")
public interface ReportController {

    @RequestMapping(value = "/total", method = RequestMethod.GET)
    @Operation(description = "Recupera o total de rebeldes e traidores", tags = {"Report API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "operação realizada com sucesso", content = {
                    @Content(mediaType =  MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ReportTotalDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<ReportTotalDTO> getTotal();


    @RequestMapping(value = "/mean", method = RequestMethod.GET)
    @Operation(description = "Recupera a média total de recursos dos rebeldes", tags = {"Report API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "operação realizada com sucesso", content = {
                    @Content(mediaType =  MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ReportMeanResourcesDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<ReportMeanResourcesDTO> getReportMean();



    @RequestMapping(value = "/lost", method = RequestMethod.GET)
    @Operation(description = "Recupera o total de pontos perdidos devido a rebeldes terem virados traidores", tags = {"Report API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "operação realizada com sucesso", content = {
                    @Content(mediaType =  MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ReportTotalPointsDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<ReportTotalPointsDTO> getTotalLostPoints();
}

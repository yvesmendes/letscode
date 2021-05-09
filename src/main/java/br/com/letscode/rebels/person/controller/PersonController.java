package br.com.letscode.rebels.person.controller;

import br.com.letscode.rebels.core.dto.DefaultError;
import br.com.letscode.rebels.item.domain.dto.ItemDTO;
import br.com.letscode.rebels.person.domain.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@RequestMapping(value = "/persons")
@Tag(name = "Rebelde API",description = "API de catálogo dos Rebeldes")
public interface PersonController {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Operation(description = "Recupera um rebelde pelo seu identificador único", tags = {"Rebeld API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "operação realizada com sucesso", content = {
                    @Content(mediaType =  MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PersonDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Rebelde não encontrado", content = {
                    @Content(mediaType =  MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DefaultError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<PersonDisplayDTO> get(@PathVariable String id);

    @RequestMapping(value = "/betray/{id}", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "operação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Rebelde não encontrado", content = {
                    @Content(mediaType =  MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DefaultError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> betray(@PathVariable String id);

    @RequestMapping(value = "/exchange/", method = RequestMethod.POST)
    @Operation(description = "Realiza a troca de itens entre os rebelds", tags = {"Rebelde API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "operação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Rebelde e/ou item não encontrado", content = @Content(schema = @Schema(implementation = DefaultError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> exchange(@Valid @RequestBody ExchangeDTO dto);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @Operation(description = "Lista todos os rebeldes do catálogo", tags = {"Rebelde API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rebeldes listados com sucesso", content = {
                    @Content(mediaType =  MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<List<PersonDisplayDTO>> get();

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @Operation(description = "Adiciona um novo rebelde ao catálogo", tags = {"Rebelde API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rebelde criado com sucesso", content = {
                    @Content(mediaType =  MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ItemDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<PersonDisplayDTO> add(@Valid @RequestBody AddPersonDTO dto);

    @RequestMapping(value = "/", method = RequestMethod.PATCH)
    @Operation(description = "Atualiza a localização de um rebelde", tags = {"Rebelde API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "loclaização atualizada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> update(@Valid @RequestBody LocalizationWithIDDTO dto);
}

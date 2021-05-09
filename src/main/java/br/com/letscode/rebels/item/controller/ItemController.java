package br.com.letscode.rebels.item.controller;

import br.com.letscode.rebels.core.dto.DefaultError;
import br.com.letscode.rebels.item.domain.dto.AddItemDTO;
import br.com.letscode.rebels.item.domain.dto.ItemDTO;
import br.com.letscode.rebels.item.domain.dto.ItemUpdateDTO;
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

@RequestMapping("/items")
@Tag(name = "Item API",description = "API de itens dos Rebeldes")
public interface ItemController {


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Operation(description = "Recupera um item pelo seu identificador único", tags = {"Item API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "operação realizada com sucesso", content = {
                    @Content(mediaType =  MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ItemDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Item não encontrado", content = {
                    @Content(mediaType =  MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DefaultError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<ItemDTO> get(@PathVariable String id);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @Operation(description = "Lista todos os itens", tags = {"Item API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "operação realizada com sucesso", content = {
                    @Content(mediaType =  MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = ItemDTO.class)))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<List<ItemDTO>> get();

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Adiciona um novo item", tags = {"Item API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "item criado com sucesso", content = {
                    @Content(mediaType =  MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ItemDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<ItemDTO> add(@Valid @RequestBody AddItemDTO dto);

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @Operation(description = "Atualiza um item existente", tags = {"Item API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "item atualizado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<Void> update(@Valid @RequestBody ItemUpdateDTO dto);
}

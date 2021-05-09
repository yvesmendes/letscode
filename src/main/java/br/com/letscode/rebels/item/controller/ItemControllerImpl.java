package br.com.letscode.rebels.item.controller;

import br.com.letscode.rebels.item.domain.dto.AddItemDTO;
import br.com.letscode.rebels.item.domain.dto.ItemDTO;
import br.com.letscode.rebels.item.domain.dto.ItemUpdateDTO;
import br.com.letscode.rebels.item.services.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ItemControllerImpl implements ItemController {

    @Autowired
    private ItemService itemService;

    private static final Logger logger = LoggerFactory.getLogger(ItemControllerImpl.class);

    @Override
    public ResponseEntity<ItemDTO> get(@PathVariable String id){
        logger.info("Recuperando o item de ID {}",id);
        return ResponseEntity.ok(this.itemService.findById(Long.parseLong(id)));
    }

    @Override
    public ResponseEntity<List<ItemDTO>> get(){
        logger.info("Listando todos os itens");
        return ResponseEntity.ok(this.itemService.findAll());
    }

    @Override
    public ResponseEntity<ItemDTO> add(@Valid @RequestBody AddItemDTO dto){
        logger.info("Adicionado um novo item {}",dto);
        return new ResponseEntity<>(itemService.add(dto), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> update(@Valid @RequestBody ItemUpdateDTO dto){
        logger.info("Atualizando um item {}",dto);
        this.itemService.update(dto);
        return ResponseEntity.ok().build();
    }
}

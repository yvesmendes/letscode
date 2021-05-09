package br.com.letscode.rebels.person.controller;

import br.com.letscode.rebels.person.domain.dto.*;
import br.com.letscode.rebels.person.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PersonControllerImpl implements PersonController {

    @Autowired
    private PersonService personService;

    @Override
    public ResponseEntity<PersonDisplayDTO> get(@PathVariable String id){
        return ResponseEntity.ok(personService.get(id));
    }

    @Override
    public ResponseEntity<Void> betray(@PathVariable(value = "id")String id){
        this.personService.betray(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> exchange(@Valid @RequestBody ExchangeDTO dto){
        this.personService.exchange(dto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<PersonDisplayDTO>> get() {
        return ResponseEntity.ok(personService.findAll());
    }

    @Override
    public ResponseEntity<PersonDisplayDTO> add(@Valid @RequestBody AddPersonDTO dto){
        return new ResponseEntity<>(this.personService.add(dto), HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<Void> update(@Valid @RequestBody LocalizationWithIDDTO dto){
        this.personService.alter(dto);
        return ResponseEntity.ok().build();
    }
}

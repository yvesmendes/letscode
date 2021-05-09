package br.com.letscode.rebels.person.services;


import br.com.letscode.rebels.core.enums.MessageEnum;
import br.com.letscode.rebels.core.exceptions.GenericException;
import br.com.letscode.rebels.core.util.Utils;
import br.com.letscode.rebels.item.domain.dto.AddInventoryItemDTO;
import br.com.letscode.rebels.item.domain.entity.Item;
import br.com.letscode.rebels.item.repository.ItemRepository;
import br.com.letscode.rebels.person.domain.dto.*;
import br.com.letscode.rebels.person.domain.entity.Localization;
import br.com.letscode.rebels.person.domain.entity.Person;
import br.com.letscode.rebels.person.domain.entity.PersonItem;
import br.com.letscode.rebels.person.domain.entity.PersonItemID;
import br.com.letscode.rebels.person.repositories.PersonItemRepository;
import br.com.letscode.rebels.person.repositories.PersonRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService{

    public static final int MINIMAL_QUANTITY = 0;
    public static final int STATUS_CODE_BAD_REQUEST = 400;
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private PersonItemRepository personItemRepository;

    @PostConstruct
    public void postConstruct(){
        modelMapper.addConverter(new Converter<PersonItem,InventoryItemDTO>() {
            @Override
            public InventoryItemDTO convert(MappingContext<PersonItem, InventoryItemDTO> mappingContext) {
                InventoryItemDTO personDisplayDTO = new InventoryItemDTO();

                personDisplayDTO.setQuantity(mappingContext.getSource().getQuantity());
                personDisplayDTO.setPersonId(mappingContext.getSource().getId().getPersonId());
                personDisplayDTO.setItemId(mappingContext.getSource().getId().getItem());

                return personDisplayDTO;
            }
        });
    }


    @Override
    @Transactional
    public PersonDisplayDTO add(AddPersonDTO dto) {

        Person person = Person.builder()
                .name(dto.getName())
                .gender(dto.getGender())
                .age(dto.getAge())
                .currentLocalization(modelMapper.map(dto.getCurrentLocalization(), Localization.class))
                .build();

        this.personRepository.saveAndFlush(person);

        Set<PersonItem> personItems = new HashSet<>();
        dto.getInventory().stream().forEach(r -> {
            PersonItem personItem =  getPersonItem(r, person);
            personItemRepository.save(personItem);
            personItems.add(personItem);
        });

        person.setInventory(personItems);

        this.personRepository.save(person);

        return modelMapper.map(person, PersonDisplayDTO.class);
    }

    private PersonItem getPersonItem(AddInventoryItemDTO r, Person person) {

        PersonItemID personItemID = new PersonItemID();

        personItemID.setItem(getItem(r.getId()).getId());
        personItemID.setPersonId(person.getId());

        PersonItem personItem = new PersonItem();
        personItem.setQuantity(r.getQuantity());
        personItem.setId(personItemID);

        return personItem;
    }

    private Item getItem(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new GenericException(MessageEnum.ITEM_NOT_FOUND.getMessage()));
    }

    @Override
    public PersonDisplayDTO get(String id) {
        return modelMapper.map(getPersonRebel(id), PersonDisplayDTO.class);
    }

    @Override
    public void alter(LocalizationWithIDDTO dto) {
        Person person = this.getPerson(dto.getId());

        if(!person.isRebel()){
            throw new GenericException(MessageEnum.INVALID_UPDATE_LOCALIZATION.getMessage(),STATUS_CODE_BAD_REQUEST);
        }

        person.updateLocalization(dto.getLatitude(),dto.getLongitude(),dto.getGalaxyName());
        this.personRepository.save(person);
    }

    @Override
    public List<PersonDisplayDTO> findAll() {
        return Utils.converEntityToDTO(PersonDisplayDTO.class,
                this.personRepository.findAllByRebelIsTrueOrderByNameAsc(),modelMapper);
    }

    @Override
    public void betray(String id) {
        Person person = this.getPersonRebel(id);
        person.betray();
        this.personRepository.save(person);
    }

    @Override
    public void exchange(ExchangeDTO dto) {

        this.validadeUniqueIds(dto);

        Person fromRebel = this.getPerson(dto.getFromPersonId());
        Person toRebel = this.getPerson(dto.getToPersonId());

        if(!fromRebel.isRebel() || !toRebel.isRebel()){
            throw new GenericException(MessageEnum.INVALID_EXCHANGE_PERSON_NOT_REBEL.getMessage(),STATUS_CODE_BAD_REQUEST);
        }

        this.validateTotal(dto);

        Map<Long,Integer> listItemsTo = new HashMap<>();
        Iterator<ItemExchangeDTO> itemExchangeDTOIterator = dto.getFromItems().iterator();

        while(itemExchangeDTOIterator.hasNext()){
            iteratorAndPrepareExchange(fromRebel, itemExchangeDTOIterator,listItemsTo);
        }

        Iterator<ItemExchangeDTO> itemToExchangeDTOIterator = dto.getToItems().iterator();
        Map<Long,Integer> listItemsFrom = new HashMap<>();

        while(itemToExchangeDTOIterator.hasNext()){
            iteratorAndPrepareExchange(toRebel, itemToExchangeDTOIterator, listItemsFrom);
        }

        prepareInventoryPostExchange(toRebel, listItemsTo);
        prepareInventoryPostExchange(fromRebel, listItemsFrom);

        personItemRepository.flush();
        personRepository.save(toRebel);
        personRepository.save(fromRebel);
    }

    @Override
    public int countAllByRebelIsTrue() {
        return this.personRepository.countAllByRebelIsTrue();
    }

    @Override
    public int countAllByRebelIsFalse() {
        return this.personRepository.countAllByRebelIsFalse();
    }

    @Override
    public int countAllBy() {
        return this.personRepository.countAllBy();
    }

    @Override
    public List<PersonDisplayDTO> findAllTraitors() {
        return Utils.converEntityToDTO(PersonDisplayDTO.class,
                this.personRepository.findAllByRebelIsFalseOrderByNameAsc(),modelMapper);
    }

    private void validadeUniqueIds(ExchangeDTO dto) {
        if(dto.getFromPersonId().equals(dto.getToPersonId())){
            throw new GenericException(MessageEnum.SAME_ID_TO_EXCHANGE.getMessage(),STATUS_CODE_BAD_REQUEST);
        }
    }

    private void prepareInventoryPostExchange(Person toRebel, Map<Long, Integer> listItemsTo) {
        for(Long key : listItemsTo.keySet() ){
            PersonItem personItem = toRebel.getInventory().stream().filter(r -> r.getId().equals(key)).findFirst().orElse(PersonItem.builder().personItemID(new PersonItemID(toRebel.getId(),key)).quantity(MINIMAL_QUANTITY).build());
            personItem.setQuantity(personItem.getQuantity() + listItemsTo.get(key));
            personItemRepository.save(personItem);
            toRebel.getInventory().add(personItem);
        }
    }

    private void validateTotal(ExchangeDTO dto) {
        int totalFromItems = dto.getFromItems().stream().map(r -> {
            Item item = this.getItem(r.getItemId());
            return item.getPoints() * r.getQuantity();
        }).collect(Collectors.summingInt(Integer::intValue));

        int totalToItems = dto.getToItems().stream().map(r -> {
            Item item = this.getItem(r.getItemId());
            return item.getPoints() * r.getQuantity();
        }).collect(Collectors.summingInt(Integer::intValue));

        if(totalFromItems != totalToItems){
            throw new GenericException(MessageEnum.TOTAL_DIVERGENCE.getMessage(), STATUS_CODE_BAD_REQUEST);
        }
    }

    private void iteratorAndPrepareExchange(Person fromRebel, Iterator<ItemExchangeDTO> itemExchangeDTOIterator, Map<Long, Integer> listItems) {
        ItemExchangeDTO itemExchangeDTO = itemExchangeDTOIterator.next();
        PersonItem personItem = fromRebel
                .getInventory()
                .stream()
                .filter(r -> r.getId().getItem().equals(itemExchangeDTO.getItemId())).findFirst()
                .orElseThrow(() -> new GenericException(MessageEnum.INVALID_EXCHANGE.getMessage()));

        if (itemExchangeDTO.getQuantity() > personItem.getQuantity()) {
            throw new GenericException(MessageEnum.INVALID_EXCHANGE_QUANTITY.getMessage(),STATUS_CODE_BAD_REQUEST);
        }

        listItems.put(itemExchangeDTO.getItemId(),itemExchangeDTO.getQuantity());

        personItem.setQuantity(personItem.getQuantity() - itemExchangeDTO.getQuantity());
    }

    private Person getPersonRebel(String id) {
        return this.personRepository.findByIdAndRebelIsTrue(id)
                .orElseThrow(() -> new GenericException(MessageEnum.PERSON_NOT_FOUND.getMessage()));
    }

    private Person getPerson(String id) {
        return this.personRepository.findById(id)
                .orElseThrow(() -> new GenericException(MessageEnum.PERSON_NOT_FOUND.getMessage()));
    }
}

package br.com.letscode.rebels;

import br.com.letscode.rebels.item.domain.dto.AddInventoryItemDTO;
import br.com.letscode.rebels.person.domain.dto.*;
import br.com.letscode.rebels.person.domain.entity.GenderEnum;
import br.com.letscode.rebels.person.domain.entity.Person;
import br.com.letscode.rebels.person.domain.entity.PersonItem;
import br.com.letscode.rebels.person.domain.entity.PersonItemID;
import br.com.letscode.rebels.person.repositories.PersonItemRepository;
import br.com.letscode.rebels.person.repositories.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = MOCK,
        classes = RebelsApplication.class)
@AutoConfigureMockMvc
public class PersonControllerTest extends BaseTest {

    public static final String PATH_PERSONS = "/persons/";
    public static final String INVALID_PATH = "sasas";
    public static final String DEFAULT_PERSON_NAME = "YVES MENDES GALVÃO";
    public static final String PATH_BETRAY = "betray/";
    public static final String PERSON_NAME_YODA = "YODA";
    public static final String PERSON_ID_FIRST = "1";
    public static final String PATH_EXCHANGE = "exchange/";
    public static final String INVALID_ID = "A";
    public static final String PATH_TOTAL = "total/";
    public static final String MAX_PERCENTAGE_VALUE = "100.0%";
    public static final String ZERO_PERCENTAGE_VALUE = "0.0%";
    public static final int TOTAL_OF_PERSONS = 2;
    public static final String PATH_MEAN = "mean/";
    public static final String FIRST_ITEM_NAME = "ARMA";
    public static final String SECOND_ITEM_NAME = "MUNIÇÃO";
    public static final String THIRD_ITEM_NAME = "ÁGUA";
    public static final String FOURTH_ITEM_NAME = "COMIDA";
    public static final String PATH_LOST = "lost";
    public static final double DEFAULT_LATITUDE = 1.0;
    public static final double DEFAULT_LONGITUDE = 1.0;
    public static final String GALAXY_NAME_VIA_LACTEA = "VIA LÁCTEA";
    public static final String GALAXY_NAME_VIA = "VIA";
    public static final String JSON_KEY_QUANTITY = "$.quantity";
    public static final String JSON_KEY_NAME = "$.name";
    public static final String JSON_ARRAY_ZERO_NAME = "$[0].name";
    public static final String JSON_TOTAL_REBELS_KEY = "$.totalRebels";
    public static final String JSON_TOTAL_TRAITORS_KEY = "$.totalTraitors";
    public static final String JSON_TOTAL_PERSONS_KEY = "$.totalOfPersons";
    public static final String FIRST_ITEM_QUANTITY = "0.5";
    public static final String SECOND_ITEM_QUANTITY = "0.0";
    public static final String THIRD_ITEM_QUANTITY = "0.0";
    public static final String FORTH_ITEM_QUANTITY = "2.0";
    public static final String ARRAY_ITEM_ZERO_NAME = "$.items[0].itemName";
    public static final String ARRAY_ITEM_ZERO_QUANTTY = "$.items[0].quantity";
    public static final String ARRAY_ITEM_ONE_NAME = "$.items[1].itemName";
    public static final String ARRAY_ITEM_ONE_QUANTITY = "$.items[1].quantity";
    public static final String ARRAY_ITEM_TWO_NAME = "$.items[2].itemName";
    public static final String ARRAY_ITEM_TWO_QUANTITY = "$.items[2].quantity";
    public static final String ARRAY_ITEM_THREE_NAME = "$.items[3].itemName";
    public static final String ARRAY_ITEM_THREE_QUANTITY = "$.items[3].quantity";
    public static final int QUANTITY_FOUR = 4;
    public static final int AGE_34 = 34;
    public static final long FOUR_ID = 4l;
    public static final int FOUR_QUANTITY = 4;
    public static final int EIGHT_QUANTITY = 8;
    public static final int QUANTITY_TWO = 2;
    private static final String REPORTS_PATH = "/reports/";
    @Autowired
    private MockMvc mvc;

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonItemRepository personItemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Person person;
    private Person person2;

    @BeforeEach
    public void prepareTest() throws Exception {
        repository.deleteAll();
        personItemRepository.deleteAll();

        Person person = this.createPerson();
        Person person2 = this.createPerson();

        repository.save(person);
        repository.save(person2);

        PersonItem personItem = PersonItem.builder()
                .personItemID(PersonItemID.builder()
                        .personId(person.getId())
                        .item(BigDecimal.ONE.longValue())
                        .build())
                .quantity(BigDecimal.ONE.intValue())
                .build();

        PersonItem personItem2 = PersonItem.builder()
                .personItemID(PersonItemID
                        .builder()
                        .personId(person2.getId())
                        .item(FOUR_ID).build())
                .quantity(FOUR_QUANTITY).build();

        personItemRepository.save(personItem);
        personItemRepository.save(personItem2);

        person.setInventory(new HashSet<>(Arrays.asList(personItem)));
        person2.setInventory(new HashSet<>(Arrays.asList(personItem2)));

        repository.save(person);
        repository.save(person2);

        this.person = person;
        this.person2 = person2;
    }

    @Test
    public void givenPersons_whenGetPersons_thenStatus200()
            throws Exception {

        mvc.perform(get(PATH_PERSONS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(JSON_ARRAY_ZERO_NAME, is(DEFAULT_PERSON_NAME)));
    }

    @Test
    public void givenPerson_whenGetPersonById_thenStatus200()
            throws Exception {

        mvc.perform(get(PATH_PERSONS + this.person.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(JSON_KEY_NAME, is(DEFAULT_PERSON_NAME)));
    }


    @Test
    public void givenPerson_whenGetPersonByInexistentId_thenStatus404()
            throws Exception {

        mvc.perform(get(PATH_PERSONS + INVALID_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenPersonId_whenBetrayCallFourTimes_thenStatus404()
            throws Exception {

        mvc.perform(post(PATH_PERSONS + PATH_BETRAY + this.person.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(post(PATH_PERSONS + PATH_BETRAY + this.person.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(post(PATH_PERSONS + PATH_BETRAY + this.person.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(post(PATH_PERSONS + PATH_BETRAY + this.person.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenPerson_whenPostPersons_thenStatus201()
            throws Exception {

        AddPersonDTO addPersonDTO = createAddPersonDTO();

        mvc.perform(post(PATH_PERSONS)
                .content(objectMapper.writeValueAsString(addPersonDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(JSON_KEY_NAME, is(PERSON_NAME_YODA)));
    }

    @Test
    public void givenPerson_whenPostPersonsWithInvalidField_thenStatus400()
            throws Exception {

        AddPersonDTO addPersonDTO = createAddPersonDTO();
        addPersonDTO.setAge(BigDecimal.ZERO.intValue());
        mvc.perform(post(PATH_PERSONS)
                .content(objectMapper.writeValueAsString(addPersonDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void givenPerson_whenPatchPersons_thenStatus200()
            throws Exception {

        LocalizationWithIDDTO localizationWithIDDTO = this.createLocalizationWithIDDTO();

        mvc.perform(patch(PATH_PERSONS)
                .content(objectMapper.writeValueAsString(localizationWithIDDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenPerson_whenPatchPersonsWithInvalidId_thenStatus404()
            throws Exception {

        LocalizationWithIDDTO localizationWithIDDTO = this.createLocalizationWithIDDTO();
        localizationWithIDDTO.setId(PERSON_ID_FIRST);
        mvc.perform(patch(PATH_PERSONS)
                .content(objectMapper.writeValueAsString(localizationWithIDDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenPerson_whenPatchPersonsWithInvalidField_thenStatus400()
            throws Exception {

        LocalizationWithIDDTO localizationWithIDDTO = this.createLocalizationWithIDDTO();
        localizationWithIDDTO.setGalaxyName(StringUtils.EMPTY);
        mvc.perform(patch(PATH_PERSONS)
                .content(objectMapper.writeValueAsString(localizationWithIDDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenPerson_whenExchange_thenStatus200()
            throws Exception {

        ExchangeDTO exchangeDTO = ExchangeDTO.builder()
                .fromPersonId(this.person.getId())
                .toPersonId(this.person2.getId())
                .fromItems(new HashSet<>(Arrays.asList(new ItemExchangeDTO(BigDecimal.ONE.longValue(), BigDecimal.ONE.intValue()))))
                .toItems(new HashSet<>(Arrays.asList(new ItemExchangeDTO(FOUR_ID, FOUR_QUANTITY))))
                .build();

        mvc.perform(post(PATH_PERSONS + PATH_EXCHANGE)
                .content(objectMapper.writeValueAsString(exchangeDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    public void givenPerson_whenExchangeWithInvalidQuantity_thenStatus400()
            throws Exception {

        ExchangeDTO exchangeDTO = createExchangeDTO();

        mvc.perform(post(PATH_PERSONS + PATH_EXCHANGE)
                .content(objectMapper.writeValueAsString(exchangeDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private ExchangeDTO createExchangeDTO() {
        return ExchangeDTO.builder().fromPersonId(this.person.getId())
                .toPersonId(this.person2.getId())
                .fromItems(new HashSet<>(Arrays.asList(ItemExchangeDTO
                        .builder()
                        .itemId(BigDecimal.ONE.longValue())
                        .quantity(QUANTITY_TWO)
                        .build())))
                .toItems(new HashSet<>(
                        Arrays.asList(ItemExchangeDTO
                                .builder()
                                .itemId(FOUR_ID)
                                .quantity(EIGHT_QUANTITY)
                                .build())))
                .build();
    }

    @Test
    public void givenPerson_whenExchangeWithInvalidTotal_thenStatus400()
            throws Exception {

        ExchangeDTO exchangeDTO = createExchange(this.person.getId(), EIGHT_QUANTITY);

        mvc.perform(post(PATH_PERSONS + PATH_EXCHANGE)
                .content(objectMapper.writeValueAsString(exchangeDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenPerson_whenExchangeWithInvalidRebelID_thenStatus404()
            throws Exception {

        ExchangeDTO exchangeDTO = createExchange(INVALID_ID, FOUR_QUANTITY);

        mvc.perform(post(PATH_PERSONS + PATH_EXCHANGE)
                .content(objectMapper.writeValueAsString(exchangeDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private ExchangeDTO createExchange(String invalidId, int fourQuantity) {
        return ExchangeDTO
                .builder()
                .fromPersonId(invalidId)
                .toPersonId(this.person2.getId())
                .fromItems(new HashSet<>(
                        Arrays.asList(
                                ItemExchangeDTO
                                        .builder()
                                        .itemId(BigDecimal.ONE.longValue())
                                        .quantity(BigDecimal.ONE.intValue())
                                        .build())))
                .toItems(new HashSet<>(
                        Arrays.asList(ItemExchangeDTO
                                .builder()
                                .itemId(FOUR_ID)
                                .quantity(fourQuantity)
                                .build())))
                .build();
    }


    @Test
    public void givenPerson_whenExchangeWithInvalidNotRebel_thenStatus400()
            throws Exception {

        ExchangeDTO exchangeDTO = ExchangeDTO.builder().fromPersonId(this.person.getId())
                .toPersonId(this.person2.getId()).fromItems(new HashSet<>(
                        Arrays.asList(
                                ItemExchangeDTO
                                        .builder()
                                        .itemId(BigDecimal.ONE.longValue())
                                        .quantity(BigDecimal.ONE.intValue())
                                        .build())))
                .fromItems(new HashSet<>(
                        Arrays.asList(ItemExchangeDTO
                                .builder()
                                .itemId(FOUR_ID)
                                .quantity(FOUR_QUANTITY)
                                .build())))
                .build();


        mvc.perform(post(PATH_PERSONS + PATH_BETRAY + this.person.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(post(PATH_PERSONS + PATH_BETRAY + this.person.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(post(PATH_PERSONS + PATH_BETRAY + this.person.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(post(PATH_PERSONS + PATH_EXCHANGE)
                .content(objectMapper.writeValueAsString(exchangeDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenReport_whenRequestTotal_thenStatus200()
            throws Exception {

        mvc.perform(get(REPORTS_PATH + PATH_TOTAL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(JSON_TOTAL_REBELS_KEY, is(MAX_PERCENTAGE_VALUE)))
                .andExpect(jsonPath(JSON_TOTAL_TRAITORS_KEY, is(ZERO_PERCENTAGE_VALUE)))
                .andExpect(jsonPath(JSON_TOTAL_PERSONS_KEY, is(TOTAL_OF_PERSONS)));
    }


    @Test
    public void givenReport_whenRequestMean_thenStatus200()
            throws Exception {

        mvc.perform(get(REPORTS_PATH + PATH_MEAN)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(ARRAY_ITEM_ZERO_NAME, is(FIRST_ITEM_NAME)))
                .andExpect(jsonPath(ARRAY_ITEM_ZERO_QUANTTY, is(FIRST_ITEM_QUANTITY)))
                .andExpect(jsonPath(ARRAY_ITEM_ONE_NAME, is(SECOND_ITEM_NAME)))
                .andExpect(jsonPath(ARRAY_ITEM_ONE_QUANTITY, is(SECOND_ITEM_QUANTITY)))
                .andExpect(jsonPath(ARRAY_ITEM_TWO_NAME, is(THIRD_ITEM_NAME)))
                .andExpect(jsonPath(ARRAY_ITEM_TWO_QUANTITY, is(THIRD_ITEM_QUANTITY)))
                .andExpect(jsonPath(ARRAY_ITEM_THREE_NAME, is(FOURTH_ITEM_NAME)))
                .andExpect(jsonPath(ARRAY_ITEM_THREE_QUANTITY, is(FORTH_ITEM_QUANTITY)));
    }


    @Test
    public void givenReport_whenRequestLostPoints_thenStatus200()
            throws Exception {

        mvc.perform(get(REPORTS_PATH + PATH_LOST)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(JSON_KEY_QUANTITY, is(BigDecimal.ZERO.intValue())));
    }


    @Test
    public void givenReport_whenRequestLostPointsWithTraitors_thenStatus200()
            throws Exception {

        mvc.perform(post(PATH_PERSONS + PATH_BETRAY + this.person.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(post(PATH_PERSONS + PATH_BETRAY + this.person.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(post(PATH_PERSONS + PATH_BETRAY + this.person.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mvc.perform(get(REPORTS_PATH + PATH_LOST)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath(JSON_KEY_QUANTITY, is(QUANTITY_FOUR)));
    }

    private LocalizationWithIDDTO createLocalizationWithIDDTO() {
        LocalizationWithIDDTO localizationWithIDDTO = new LocalizationWithIDDTO();
        localizationWithIDDTO.setId(person.getId());
        localizationWithIDDTO.setGalaxyName(GALAXY_NAME_VIA);
        localizationWithIDDTO.setLatitude(DEFAULT_LATITUDE);
        localizationWithIDDTO.setLongitude(DEFAULT_LONGITUDE);

        return localizationWithIDDTO;
    }

    private AddPersonDTO createAddPersonDTO() {

        LocalizationDTO localizationDTO = LocalizationDTO
                .builder()
                .galaxyName(GALAXY_NAME_VIA_LACTEA)
                .latitude(DEFAULT_LATITUDE)
                .longitude(DEFAULT_LONGITUDE)
                .build();

        AddPersonDTO addPersonDTO = AddPersonDTO.builder()
                .age(AGE_34)
                .gender(GenderEnum.M)
                .name(PERSON_NAME_YODA)
                .currentLocalization(localizationDTO)
                .build();

        AddInventoryItemDTO addInventoryItemDTO = new AddInventoryItemDTO();

        addInventoryItemDTO.setId(BigDecimal.ONE.longValue());
        addInventoryItemDTO.setQuantity(BigDecimal.TEN.intValue());
        addPersonDTO.setInventory(new HashSet<>(Arrays.asList(addInventoryItemDTO)));
        return addPersonDTO;
    }
}

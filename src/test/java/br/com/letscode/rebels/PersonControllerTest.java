package br.com.letscode.rebels;

import br.com.letscode.rebels.item.domain.dto.AddInventoryItemDTO;
import br.com.letscode.rebels.item.repository.ItemRepository;
import br.com.letscode.rebels.person.domain.dto.*;
import br.com.letscode.rebels.person.domain.entity.GenderEnum;
import br.com.letscode.rebels.person.domain.entity.Person;
import br.com.letscode.rebels.person.domain.entity.PersonItem;
import br.com.letscode.rebels.person.domain.entity.PersonItemID;
import br.com.letscode.rebels.person.repositories.PersonItemRepository;
import br.com.letscode.rebels.person.repositories.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = MOCK,
		classes = RebelsApplication.class)
@AutoConfigureMockMvc
public class PersonControllerTest extends BaseTest {

	public static final String PERSONS_PATH = "/persons/";
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

		PersonItem personItem = PersonItem.builder().personItemID(new PersonItemID(person.getId(),1l)).quantity(1).build();
		PersonItem personItem2 = PersonItem.builder().personItemID(new PersonItemID(person2.getId(),4l)).quantity(4).build();

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

		mvc.perform(get("/persons/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].name", is("YVES MENDES GALVÃO")));
	}

	@Test
	public void givenPerson_whenGetPersonById_thenStatus200()
			throws Exception {

		mvc.perform(get("/persons/"+this.person.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name", is("YVES MENDES GALVÃO")));
	}


	@Test
	public void givenPerson_whenGetPersonByInexistentId_thenStatus404()
			throws Exception {

		mvc.perform(get("/persons/sasas")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void givenPersonId_whenBetrayCallFourTimes_thenStatus404()
			throws Exception {

		mvc.perform(post(PERSONS_PATH+"betray/"+this.person.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(post(PERSONS_PATH+"betray/"+this.person.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(post(PERSONS_PATH+"betray/"+this.person.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(post(PERSONS_PATH+"betray/"+this.person.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void givenPerson_whenPostPersons_thenStatus201()
			throws Exception {

		AddPersonDTO addPersonDTO = createAddPersonDTO();

		mvc.perform(post(PERSONS_PATH)
				.content(objectMapper.writeValueAsString(addPersonDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name", is("YODA")));
	}

	@Test
	public void givenPerson_whenPostPersonsWithInvalidField_thenStatus400()
			throws Exception {

		AddPersonDTO addPersonDTO = createAddPersonDTO();
		addPersonDTO.setAge(0);
		mvc.perform(post(PERSONS_PATH)
				.content(objectMapper.writeValueAsString(addPersonDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}


	@Test
	public void givenPerson_whenPatchPersons_thenStatus200()
			throws Exception {

		LocalizationWithIDDTO localizationWithIDDTO = this.createLocalizationWithIDDTO();

		mvc.perform(patch(PERSONS_PATH)
				.content(objectMapper.writeValueAsString(localizationWithIDDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void givenPerson_whenPatchPersonsWithInvalidId_thenStatus404()
			throws Exception {

		LocalizationWithIDDTO localizationWithIDDTO = this.createLocalizationWithIDDTO();
		localizationWithIDDTO.setId("1");
		mvc.perform(patch(PERSONS_PATH)
				.content(objectMapper.writeValueAsString(localizationWithIDDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void givenPerson_whenPatchPersonsWithInvalidField_thenStatus400()
			throws Exception {

		LocalizationWithIDDTO localizationWithIDDTO = this.createLocalizationWithIDDTO();
		localizationWithIDDTO.setGalaxyName("");
		mvc.perform(patch(PERSONS_PATH)
				.content(objectMapper.writeValueAsString(localizationWithIDDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenPerson_whenExchange_thenStatus200()
			throws Exception {

		ExchangeDTO exchangeDTO = new ExchangeDTO();

		exchangeDTO.setFromPersonId(this.person.getId());
		exchangeDTO.setToPersonId(this.person2.getId());

		exchangeDTO.setFromItems(new HashSet<>(Arrays.asList(new ItemExchangeDTO(1l,1))));
		exchangeDTO.setToItems(new HashSet<>(Arrays.asList(new ItemExchangeDTO(4l,4))));

		mvc.perform(post(PERSONS_PATH+"exchange/")
				.content(objectMapper.writeValueAsString(exchangeDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}


	@Test
	public void givenPerson_whenExchangeWithInvalidQuantity_thenStatus400()
			throws Exception {

		ExchangeDTO exchangeDTO = new ExchangeDTO();

		exchangeDTO.setFromPersonId(this.person.getId());
		exchangeDTO.setToPersonId(this.person2.getId());

		exchangeDTO.setFromItems(new HashSet<>(Arrays.asList(new ItemExchangeDTO(1l,2))));
		exchangeDTO.setToItems(new HashSet<>(Arrays.asList(new ItemExchangeDTO(4l,8))));

		mvc.perform(post(PERSONS_PATH+"exchange/")
				.content(objectMapper.writeValueAsString(exchangeDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenPerson_whenExchangeWithInvalidTotal_thenStatus400()
			throws Exception {

		ExchangeDTO exchangeDTO = new ExchangeDTO();

		exchangeDTO.setFromPersonId(this.person.getId());
		exchangeDTO.setToPersonId(this.person2.getId());

		exchangeDTO.setFromItems(new HashSet<>(Arrays.asList(new ItemExchangeDTO(1l,1))));
		exchangeDTO.setToItems(new HashSet<>(Arrays.asList(new ItemExchangeDTO(4l,8))));

		mvc.perform(post(PERSONS_PATH+"exchange/")
				.content(objectMapper.writeValueAsString(exchangeDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenPerson_whenExchangeWithInvalidRebelID_thenStatus404()
			throws Exception {

		ExchangeDTO exchangeDTO = new ExchangeDTO();

		exchangeDTO.setFromPersonId("A");
		exchangeDTO.setToPersonId(this.person2.getId());

		exchangeDTO.setFromItems(new HashSet<>(Arrays.asList(new ItemExchangeDTO(1l,1))));
		exchangeDTO.setToItems(new HashSet<>(Arrays.asList(new ItemExchangeDTO(4l,4))));

		mvc.perform(post(PERSONS_PATH+"exchange/")
				.content(objectMapper.writeValueAsString(exchangeDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}


	@Test
	public void givenPerson_whenExchangeWithInvalidNotRebel_thenStatus400()
			throws Exception {

		ExchangeDTO exchangeDTO = new ExchangeDTO();

		exchangeDTO.setFromPersonId(this.person.getId());
		exchangeDTO.setToPersonId(this.person2.getId());

		exchangeDTO.setFromItems(new HashSet<>(Arrays.asList(new ItemExchangeDTO(1l,1))));
		exchangeDTO.setToItems(new HashSet<>(Arrays.asList(new ItemExchangeDTO(4l,4))));

		mvc.perform(post(PERSONS_PATH+"betray/"+this.person.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(post(PERSONS_PATH+"betray/"+this.person.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(post(PERSONS_PATH+"betray/"+this.person.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(post(PERSONS_PATH+"exchange/")
				.content(objectMapper.writeValueAsString(exchangeDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenReport_whenRequestTotal_thenStatus200()
			throws Exception {

		mvc.perform(get(REPORTS_PATH+"total/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalRebels", is("100.0%")))
		.andExpect(jsonPath("$.totalTraitors", is("0.0%")))
		.andExpect(jsonPath("$.totalOfPersons", is(2)));
	}


	@Test
	public void givenReport_whenRequestMean_thenStatus200()
			throws Exception {

		mvc.perform(get(REPORTS_PATH+"mean/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.items[0].itemName", is("ARMA")))
				.andExpect(jsonPath("$.items[0].quantity", is("0.5")))
				.andExpect(jsonPath("$.items[1].itemName", is("MUNIÇÃO")))
				.andExpect(jsonPath("$.items[1].quantity", is("0.0")))
				.andExpect(jsonPath("$.items[2].itemName", is("ÁGUA")))
				.andExpect(jsonPath("$.items[2].quantity", is("0.0")))
				.andExpect(jsonPath("$.items[3].itemName", is("COMIDA")))
				.andExpect(jsonPath("$.items[3].quantity", is("2.0")));
	}


	@Test
	public void givenReport_whenRequestLostPoints_thenStatus200()
			throws Exception {

		mvc.perform(get(REPORTS_PATH+"lost")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.quantity", is(0)));
	}


	@Test
	public void givenReport_whenRequestLostPointsWithTraitors_thenStatus200()
			throws Exception {

		mvc.perform(post(PERSONS_PATH+"betray/"+this.person.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(post(PERSONS_PATH+"betray/"+this.person.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(post(PERSONS_PATH+"betray/"+this.person.getId())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		mvc.perform(get(REPORTS_PATH+"lost")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.quantity", is(4)));
	}

	private LocalizationWithIDDTO createLocalizationWithIDDTO() {
		LocalizationWithIDDTO localizationWithIDDTO = new LocalizationWithIDDTO();
		localizationWithIDDTO.setId(person.getId());
		localizationWithIDDTO.setGalaxyName("VIA");
		localizationWithIDDTO.setLatitude(1.0);
		localizationWithIDDTO.setLongitude(1.0);

		return localizationWithIDDTO;
	}

	private AddPersonDTO createAddPersonDTO() {
		LocalizationDTO localizationDTO = new LocalizationDTO();
		localizationDTO.setGalaxyName("VIA LÁCTEA");
		localizationDTO.setLatitude(1.0);
		localizationDTO.setLongitude(1.0);

		AddPersonDTO addPersonDTO = new AddPersonDTO();
		addPersonDTO.setAge(34);
		addPersonDTO.setGender(GenderEnum.M);
		addPersonDTO.setName("YODA");
		addPersonDTO.setCurrentLocalization(localizationDTO);

		AddInventoryItemDTO addInventoryItemDTO = new AddInventoryItemDTO();

		addInventoryItemDTO.setId(1l);
		addInventoryItemDTO.setQuantity(10);
		addPersonDTO.setInventory(new HashSet<>(Arrays.asList(addInventoryItemDTO)));
		return addPersonDTO;
	}
}

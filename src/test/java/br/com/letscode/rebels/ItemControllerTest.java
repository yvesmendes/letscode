package br.com.letscode.rebels;

import br.com.letscode.rebels.item.domain.dto.AddItemDTO;
import br.com.letscode.rebels.item.domain.dto.ItemUpdateDTO;
import br.com.letscode.rebels.item.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = MOCK,
		classes = RebelsApplication.class)
@AutoConfigureMockMvc
public class ItemControllerTest extends BaseTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;


	@Autowired
	private ItemRepository itemRepository;

	@Test
	public void givenItems_whenGetItems_thenStatus200()
			throws Exception {

		mvc.perform(get("/items/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].name", is("ARMA")));
	}

	@Test
	public void givenItem_whenPostItem_thenStatus201()
			throws Exception {


		AddItemDTO itemDTO = new AddItemDTO();
		itemDTO.setName("ARMA");
		itemDTO.setPoints(4);

		mvc.perform(post("/items/")
				.content(objectMapper.writeValueAsString(itemDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name", is("ARMA")));
	}

	@Test
	public void givenInvalidItem_whenPostItem_thenStatus400()
			throws Exception {


		AddItemDTO itemDTO = new AddItemDTO();
		itemDTO.setName("ARMA");
		itemDTO.setPoints(0);

		mvc.perform(post("/items/")
				.content(objectMapper.writeValueAsString(itemDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}


	@Test
	public void givenItem_whenUpdateItem_thenStatus200() throws Exception {

		ItemUpdateDTO itemDTO = new ItemUpdateDTO();
		itemDTO.setName("ARMA");
		itemDTO.setPoints(4);
		itemDTO.setId(1l);

		mvc.perform(put("/items/")
				.content(objectMapper.writeValueAsString(itemDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void givenItem_whenGetItemByInexistentId_thenStatus404() throws Exception {
		mvc.perform(get("/items/1000")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	@Order(0)
	public void givenItem_whenGetItemById_thenStatus200() throws Exception {
		mvc.perform(get("/items/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name", is("ARMA")));
	}
}

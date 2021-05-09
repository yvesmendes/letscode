package br.com.letscode.rebels;

import br.com.letscode.rebels.person.domain.entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonTest extends BaseTest {


	public static final String NAME = "Yves Mendes Galvão";
	public static final int AGE = 34;
	public static final String GALAXY_NAME = "Via láctea";
	public static final String MSG_NAME_UPPERCASE = "Nome do rebelde deve ser o nome cadastrado em upper case";
	public static final String MSG_GALAXY_NAME_UPPERCASE = "Nome da deve ser o nome cadastrado em upper case";
	public static final String GALAXY_NAME_ORION = "ORION";
	public static final double NEW_LATITUDE = 1.0;
	public static final double NEW_LONGITUDE = 1.0;

	@Test
	@DisplayName("Simple create person")
	public void givenAPerson_whenBuilderAPerson_thenRebelIsTrue() {
		Person person = createPerson();

		assertTrue(person.isRebel(), "Novas pessoas cadastradas devem ser rebeldes");
		assertEquals(NAME,person.getName(),"Nome do rebelde deve ser igual ao cadastrado");
		assertEquals(GALAXY_NAME, person.getCurrentLocalization().getGalaxyName(), "Nome da deve ser igual ao nome cadastrado");
		assertEquals(AGE, person.getAge(), "Nome da deve ser o nome cadastrado em upper case");
	}

	@Test
	@DisplayName("Identify a traitor")
	public void givenAPerson_whenCallBetrayThretimes_thenRebelIsFalse() {
		Person person = createPerson();

		assertTrue(person.isRebel(), "Novas pessoas cadastradas devem ser rebeldes");

		person.betray();
		person.betray();
		person.betray();

		assertTrue(!person.isRebel(), "A pessoa deve ser uma triadora após chamar o método betray 3 vezes");
	}

	@Test
	@DisplayName("Prepare a person to commit")
	public void givenAPerson_whenCallPrepare_thenNameAndGalaxyNameIsUpperCase() {
		Person person = createPerson();
		person.prepare();
		assertEquals(NAME.toUpperCase(),person.getName(),MSG_NAME_UPPERCASE);
		assertEquals(GALAXY_NAME.toUpperCase(), person.getCurrentLocalization().getGalaxyName(),  MSG_GALAXY_NAME_UPPERCASE);
	}


	@Test
	@DisplayName("Update the localization")
	public void givenAPerson_whenCallUpdate_thenTheNewLocalizationIsShow() {
		Person person = createPerson();
		person.updateLocalization(NEW_LATITUDE, NEW_LONGITUDE, GALAXY_NAME_ORION);
		assertEquals(GALAXY_NAME_ORION, person.getCurrentLocalization().getGalaxyName(),  MSG_GALAXY_NAME_UPPERCASE);
		assertEquals(NEW_LATITUDE, person.getCurrentLocalization().getLatitude(),  MSG_GALAXY_NAME_UPPERCASE);
		assertEquals(NEW_LONGITUDE, person.getCurrentLocalization().getLongitude(),  MSG_GALAXY_NAME_UPPERCASE);
	}
}

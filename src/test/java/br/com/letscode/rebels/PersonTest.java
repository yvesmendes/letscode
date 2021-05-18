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
	public static final String MSG_NEW_PERSONS_TEST = "Novas pessoas cadastradas devem ser rebeldes";
	public static final String MSG_PERSON_NAME_HAS_THE_SAME = "Nome do rebelde deve ser igual ao cadastrado";
	public static final String MSG_GALAXY_NAME_HAS_THE_SAME = "Nome da deve ser igual ao nome cadastrado";
	public static final String MSG_AGE_HAS_THE_SAME = "Nome da deve ser o nome cadastrado em upper case";
	public static final String MSG_PERSON_BE_A_TRAITOR_AFTER_THREE_TIMES = "A pessoa deve ser uma traidora após chamar o método betray 3 vezes";

	@Test
	@DisplayName("Simple create person")
	public void givenAPerson_whenBuilderAPerson_thenRebelIsTrue() {
		Person person = createPerson();

		assertTrue(person.isRebel(), MSG_NEW_PERSONS_TEST);
		assertEquals(NAME,person.getName(), MSG_PERSON_NAME_HAS_THE_SAME);
		assertEquals(GALAXY_NAME, person.getCurrentLocalization().getGalaxyName(), MSG_GALAXY_NAME_HAS_THE_SAME);
		assertEquals(AGE, person.getAge(), MSG_AGE_HAS_THE_SAME);
	}

	@Test
	@DisplayName("Identify a traitor")
	public void givenAPerson_whenCallBetrayThretimes_thenRebelIsFalse() {
		Person person = createPerson();

		assertTrue(person.isRebel(), MSG_NEW_PERSONS_TEST);

		person.betray();
		person.betray();
		person.betray();

		assertTrue(!person.isRebel(), MSG_PERSON_BE_A_TRAITOR_AFTER_THREE_TIMES);
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

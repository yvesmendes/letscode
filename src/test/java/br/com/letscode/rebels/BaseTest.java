package br.com.letscode.rebels;

import br.com.letscode.rebels.item.domain.entity.Item;
import br.com.letscode.rebels.item.repository.ItemRepository;
import br.com.letscode.rebels.person.domain.entity.GenderEnum;
import br.com.letscode.rebels.person.domain.entity.Localization;
import br.com.letscode.rebels.person.domain.entity.Person;

public abstract class BaseTest {

	protected static final String NAME = "Yves Mendes Galvão";
	protected static final int AGE = 34;
	protected static final String GALAXY_NAME = "Via láctea";

	protected Person createPerson() {
		return Person.builder()
				.age(AGE)
				.gender(GenderEnum.M)
				.name(NAME)
				.currentLocalization(new Localization(0.1,0.1, GALAXY_NAME))
				.build();
	}

	protected Item createItem(String name, int points) {
		return Item.builder()
				.name(name)
				.points(points)
				.build();
	}
}

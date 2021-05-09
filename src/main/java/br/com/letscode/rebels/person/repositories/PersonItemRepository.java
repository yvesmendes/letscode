package br.com.letscode.rebels.person.repositories;


import br.com.letscode.rebels.person.domain.entity.PersonItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonItemRepository extends JpaRepository<PersonItem,Long> {

}

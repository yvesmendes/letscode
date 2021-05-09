package br.com.letscode.rebels.person.repositories;


import br.com.letscode.rebels.person.domain.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person,String> {
    List<Person> findAllByRebelIsTrueOrderByNameAsc();
    List<Person> findAllByRebelIsFalseOrderByNameAsc();
    Optional<Person> findByIdAndRebelIsTrue(String id);
    Integer countAllByRebelIsTrue();
    Integer countAllBy();
    Integer countAllByRebelIsFalse();
}

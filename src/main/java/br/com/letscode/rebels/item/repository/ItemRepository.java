package br.com.letscode.rebels.item.repository;


import br.com.letscode.rebels.item.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {

    List<Item> findAllByOrderByNameAsc();
}

package br.com.letscode.rebels.item.services;

import br.com.letscode.rebels.item.domain.dto.AddItemDTO;
import br.com.letscode.rebels.item.domain.dto.ItemDTO;
import br.com.letscode.rebels.item.domain.dto.ItemUpdateDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {
    ItemDTO findById(Long id);
    List<ItemDTO> findAll();

    ItemDTO add(AddItemDTO dto);

    void update(ItemUpdateDTO dto);
}

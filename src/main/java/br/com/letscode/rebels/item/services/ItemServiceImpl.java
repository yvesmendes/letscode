package br.com.letscode.rebels.item.services;


import br.com.letscode.rebels.core.enums.MessageEnum;
import br.com.letscode.rebels.core.exceptions.GenericException;
import br.com.letscode.rebels.core.util.Utils;
import br.com.letscode.rebels.item.domain.dto.AddItemDTO;
import br.com.letscode.rebels.item.domain.dto.ItemDTO;
import br.com.letscode.rebels.item.domain.dto.ItemUpdateDTO;
import br.com.letscode.rebels.item.domain.entity.Item;
import br.com.letscode.rebels.item.repository.ItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ItemDTO findById(Long id) {
        return modelMapper.map(getItem(id), ItemDTO.class);
    }

    private Item getItem(Long id) {
        return this.itemRepository.findById(id).orElseThrow(() -> new GenericException(MessageEnum.ITEM_NOT_FOUND.getMessage()));
    }

    @Override
    public List<ItemDTO> findAll() {
        return Utils.converEntityToDTO(ItemDTO.class,this.itemRepository.findAllByOrderByNameAsc(), modelMapper);
    }

    @Override
    public ItemDTO add(AddItemDTO dto) {
        Item item = Item.builder().name(dto.getName()).points(dto.getPoints()).build();
        this.itemRepository.save(item);
        return modelMapper.map(item,ItemDTO.class);
    }

    @Override
    public void update(ItemUpdateDTO dto) {
        Item item = this.getItem(dto.getId());
        item.setName(dto.getName());
        item.setPoints(dto.getPoints());
        this.itemRepository.save(item);
    }
}

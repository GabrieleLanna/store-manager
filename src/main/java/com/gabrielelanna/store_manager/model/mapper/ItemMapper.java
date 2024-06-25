package com.gabrielelanna.store_manager.model.mapper;

import com.gabrielelanna.store_manager.model.dto.ItemDTO;
import com.gabrielelanna.store_manager.model.entity.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public ItemDTO toDTO(Item item) {
        if (item == null) {
            return null;
        }
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setCategory(item.getCategory());
        itemDTO.setAvailable(item.isAvailable());
        itemDTO.setPrice(item.getPrice());
        return itemDTO;
    }

    public Item toEntity(ItemDTO itemDTO) {
        if (itemDTO == null) {
            return null;
        }
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setDescription(itemDTO.getDescription());
        item.setCategory(itemDTO.getCategory());
        item.setAvailable(itemDTO.isAvailable());
        item.setPrice(itemDTO.getPrice());
        return item;
    }
}

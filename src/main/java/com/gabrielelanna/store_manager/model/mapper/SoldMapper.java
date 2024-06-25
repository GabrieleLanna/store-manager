package com.gabrielelanna.store_manager.model.mapper;

import com.gabrielelanna.store_manager.model.dto.ItemDTO;
import com.gabrielelanna.store_manager.model.dto.SoldDTO;
import com.gabrielelanna.store_manager.model.entity.Item;
import com.gabrielelanna.store_manager.model.entity.Sold;
import org.springframework.stereotype.Component;

@Component
public class SoldMapper {

    private final ItemMapper itemMapper;

    public SoldMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public SoldDTO toDTO(Sold sold) {
        if (sold == null) {
            return null;
        }
        SoldDTO soldDTO = new SoldDTO();
        soldDTO.setId(sold.getId());
        soldDTO.setItem(itemMapper.toDTO(sold.getItem()));
        soldDTO.setSaleDate(sold.getSaleDate());
        return soldDTO;
    }

    public Sold toEntity(SoldDTO soldDTO) {
        if (soldDTO == null) {
            return null;
        }
        Sold sold = new Sold();
        sold.setId(soldDTO.getId());
        sold.setItem(itemMapper.toEntity(soldDTO.getItem()));
        sold.setSaleDate(soldDTO.getSaleDate());
        return sold;
    }
}

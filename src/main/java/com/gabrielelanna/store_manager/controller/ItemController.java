package com.gabrielelanna.store_manager.controller;

import com.gabrielelanna.store_manager.model.dto.ItemDTO;
import com.gabrielelanna.store_manager.model.entity.Item;
import com.gabrielelanna.store_manager.model.mapper.ItemMapper;
import com.gabrielelanna.store_manager.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemMapper itemMapper;
    private final ItemService itemService;

    @GetMapping("/search")
    public ResponseEntity<List<ItemDTO>> searchItemsByDescription(@RequestParam String description) {
        List<Item> items = itemService.findItemsByDescription(description);
        List<ItemDTO> itemDTOs = items.stream()
                .map(itemMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(itemDTOs);
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        List<ItemDTO> itemDTOList = itemService.getAllItems().stream()
                .map(itemMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(itemDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id)
                .map(itemMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO itemDTO) {
        Item item = itemMapper.toEntity(itemDTO);
        Item savedItem = itemService.saveItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemMapper.toDTO(savedItem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemDTO> updateItem(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
        return itemService.getItemById(id)
                .map(existingItem -> {
                    Item item = itemMapper.toEntity(itemDTO);
                    item.setId(existingItem.getId());
                    Item updatedItem = itemService.saveItem(item);
                    return ResponseEntity.ok(itemMapper.toDTO(updatedItem));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItem(@PathVariable Long id) {
        return itemService.getItemById(id)
                .map(existingItem -> {
                    itemService.deleteItem(existingItem.getId());
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
package com.gabrielelanna.store_manager.controller;

import com.gabrielelanna.store_manager.model.dto.SoldDTO;
import com.gabrielelanna.store_manager.model.entity.Sold;
import com.gabrielelanna.store_manager.model.mapper.SoldMapper;
import com.gabrielelanna.store_manager.service.SoldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sold")
@RequiredArgsConstructor
public class SoldController {

    private final SoldService soldService;
    private final SoldMapper soldMapper;

    @GetMapping
    public ResponseEntity<List<SoldDTO>> getAllSoldItems() {
        List<SoldDTO> soldDTOList = soldService.getAllSoldItems().stream()
                .map(soldMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(soldDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoldDTO> getSoldItemById(@PathVariable Long id) {
        return soldService.getSoldById(id)
                .map(soldMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SoldDTO> createSoldItem(@RequestBody SoldDTO soldDTO) {
        Sold sold = soldMapper.toEntity(soldDTO);
        Sold savedSold = soldService.saveSold(sold);
        return ResponseEntity.status(HttpStatus.CREATED).body(soldMapper.toDTO(savedSold));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SoldDTO> updateSoldItem(@PathVariable Long id, @RequestBody SoldDTO soldDTO) {
        return soldService.getSoldById(id)
                .map(existingSold -> {
                    Sold sold = soldMapper.toEntity(soldDTO);
                    sold.setId(existingSold.getId());
                    Sold updatedSold = soldService.saveSold(sold);
                    return ResponseEntity.ok(soldMapper.toDTO(updatedSold));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSoldItem(@PathVariable Long id) {
        return soldService.getSoldById(id)
                .map(existingSold -> {
                    soldService.deleteSold(existingSold.getId());
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
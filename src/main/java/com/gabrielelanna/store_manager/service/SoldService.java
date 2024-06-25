package com.gabrielelanna.store_manager.service;

import com.gabrielelanna.store_manager.model.entity.Sold;
import com.gabrielelanna.store_manager.repository.SoldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SoldService {

    private final SoldRepository soldRepository;

    public List<Sold> getAllSoldItems() {
        return soldRepository.findAll();
    }

    public Optional<Sold> getSoldById(Long id) {
        return soldRepository.findById(id);
    }

    public Sold saveSold(Sold sold) {
        return soldRepository.save(sold);
    }

    public void deleteSold(Long id) {
        soldRepository.deleteById(id);
    }
}
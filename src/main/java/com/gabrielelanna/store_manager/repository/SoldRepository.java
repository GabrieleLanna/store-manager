package com.gabrielelanna.store_manager.repository;

import com.gabrielelanna.store_manager.model.entity.Sold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoldRepository extends JpaRepository<Sold, Long> {
}

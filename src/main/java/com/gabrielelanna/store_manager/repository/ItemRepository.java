package com.gabrielelanna.store_manager.repository;

import com.gabrielelanna.store_manager.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.description LIKE %:description%")
    List<Item> findByDescriptionLike(@Param("description") String description);

}
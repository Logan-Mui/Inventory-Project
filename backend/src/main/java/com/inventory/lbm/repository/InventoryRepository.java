package com.inventory.lbm.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.lbm.entity.InventoryEntity;

public interface InventoryRepository extends JpaRepository<InventoryEntity, String>{
    Page<InventoryEntity> findByNameContaining(String name, Pageable pageable);
}

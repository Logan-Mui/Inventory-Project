package com.inventory.lbm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.inventory.lbm.entity.InventoryEntity;
import com.inventory.lbm.entity.VendorEntity;

public interface InventoryStoreService {
    public InventoryEntity createInventory(String listId, String name, int quantity, VendorEntity vendorRef, String editSequence);

    public Page<InventoryEntity> getItems(Pageable pageable);

    public Optional<InventoryEntity> getItemByID(String listId);

    public Page<InventoryEntity> getItemByName(Pageable pageable, String name);

    public void storeResponse(List<InventoryEntity> itemList);
}

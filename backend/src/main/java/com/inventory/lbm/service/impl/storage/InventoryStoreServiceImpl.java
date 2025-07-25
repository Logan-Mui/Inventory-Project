package com.inventory.lbm.service.impl.storage;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inventory.lbm.entity.InventoryEntity;
import com.inventory.lbm.entity.VendorEntity;
import com.inventory.lbm.repository.InventoryRepository;
import com.inventory.lbm.service.InventoryStoreService;


@Service
public class InventoryStoreServiceImpl implements InventoryStoreService {

    private final InventoryRepository inventoryRepository;

    public InventoryStoreServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public InventoryEntity createInventory(String listId, String name, int quantity, VendorEntity vendorRef, String editSequence) {
        InventoryEntity item = new InventoryEntity(listId, name, quantity, vendorRef, editSequence);
        inventoryRepository.save(item);
        return item;
    }

    @Override
    public Page<InventoryEntity> getItems(Pageable pageable) {
       return inventoryRepository.findAll(pageable);
    }

    @Override
    public Optional<InventoryEntity> getItemByID(String listId) {
       return inventoryRepository.findById(listId);
    }

    @Override
    public Page<InventoryEntity> getItemByName(Pageable pageable,String name) {
        return inventoryRepository.findByNameContaining(name, pageable);
    }
    

    @Override
    public void storeResponse(List<InventoryEntity> itemList) {
        for(InventoryEntity item : itemList) {
            inventoryRepository.save(item);
        }
    }
}
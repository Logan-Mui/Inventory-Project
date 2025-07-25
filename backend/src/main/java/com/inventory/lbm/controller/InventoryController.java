package com.inventory.lbm.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.lbm.dto.InventoryDTO;
import com.inventory.lbm.entity.InventoryEntity;
import com.inventory.lbm.mapper.InventoryMapper;
import com.inventory.lbm.service.InventoryStoreService;
import com.inventory.lbm.service.impl.storage.InventoryStoreServiceImpl;



@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    
    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    private final InventoryStoreService inventoryService;

    private final InventoryMapper inventoryMapper;

    public InventoryController(InventoryStoreServiceImpl inventoryService, InventoryMapper inventoryMapper) {
        this.inventoryService = inventoryService;
        this.inventoryMapper = inventoryMapper;
    }

    @GetMapping("/")
    public ResponseEntity<Page<InventoryDTO>> getInventory(@PageableDefault(page = 0, size = 10) Pageable pageable) {

        logger.info("INVREPO - Recieved get all request.");

        Page<InventoryEntity> entities = inventoryService.getItems(pageable);
        Page<InventoryDTO> dtoPage = inventoryMapper.toDTOPage(entities);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<InventoryDTO> getItemByID(@RequestParam String listID) {

        logger.info("INVREPO - Recieved get by ID request.");

        Optional<InventoryEntity> optionalItem = inventoryService.getItemByID(listID);
        return optionalItem.map(inventoryMapper::toDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/page/{page}/name/{name}")
    public ResponseEntity<Page<InventoryDTO>> getItemByName(@PageableDefault(page = 0, size = 10) Pageable pageable, @RequestParam String name) {
        
        logger.info("INVREPO - Recieved get by Name request.");
        
        Page<InventoryEntity> entities = inventoryService.getItemByName(pageable, name);
        Page<InventoryDTO> dtoPage = inventoryMapper.toDTOPage(entities);
        return ResponseEntity.ok(dtoPage);
    }
    
    
    
}

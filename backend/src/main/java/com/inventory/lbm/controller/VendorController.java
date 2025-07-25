package com.inventory.lbm.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.lbm.dto.VendorDTO;
import com.inventory.lbm.entity.VendorEntity;
import com.inventory.lbm.mapper.VendorMapper;
import com.inventory.lbm.service.VendorStoreService;
import com.inventory.lbm.service.impl.storage.VendorStoreServiceImpl;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private static final Logger logger = LoggerFactory.getLogger(VendorController.class);

    private final VendorStoreService vendorService;

    private final VendorMapper vendorMapper;

    public VendorController(VendorStoreServiceImpl vendorService, VendorMapper vendorMapper) {
        this.vendorService = vendorService;
        this.vendorMapper = vendorMapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<VendorDTO>> getAllVendors() {

        logger.info("VENDREPO - Recieved get all request.");

        List<VendorEntity> entityList = vendorService.getVendors();
        List<VendorDTO> dtoList = vendorMapper.toDTOList(entityList);
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/paginated/")
    public ResponseEntity<Page<VendorDTO>> getAllVendorsPaginated(@PageableDefault(page = 0, size = 10) Pageable pageable) {

        logger.info("VENDREPO - Recieved get all paginated request.");

        Page<VendorEntity> entityPage = vendorService.getVendorsPaginated(pageable);
        Page<VendorDTO> entityDTO = vendorMapper.toDTOPage(entityPage);
        return ResponseEntity.ok(entityDTO);
    }
}

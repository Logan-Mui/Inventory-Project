package com.inventory.lbm.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.inventory.lbm.entity.VendorEntity;

public interface VendorStoreService {
    
    public VendorEntity createVendor(String listId, String fullname, String companyName, String phone, boolean active);

    public List<VendorEntity> getVendors();

    public Page<VendorEntity> getVendorsPaginated(Pageable pageable);

    public Optional<VendorEntity> getVendor(String listId);

    public void storeResponse(List<VendorEntity> vendorList);
}

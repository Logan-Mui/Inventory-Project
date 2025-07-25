package com.inventory.lbm.service.impl.storage;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inventory.lbm.entity.VendorEntity;
import com.inventory.lbm.repository.VendorRepository;
import com.inventory.lbm.service.VendorStoreService;

@Service
public class VendorStoreServiceImpl implements VendorStoreService{

    private final VendorRepository vendorRepository;

    public VendorStoreServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public VendorEntity createVendor(String listId, String fullname, String companyName, String phone, boolean active) {
        VendorEntity request = new VendorEntity(listId, fullname, companyName, phone, active);
        vendorRepository.save(request);
        return request;
    }

    @Override
    public List<VendorEntity> getVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public Page<VendorEntity> getVendorsPaginated(Pageable pageable) {
        return vendorRepository.findAll(pageable);
    }

    @Override
    public Optional<VendorEntity> getVendor(String listId) {
        return vendorRepository.findById(listId);
    }

    @Override
    public void storeResponse(List<VendorEntity> vendorList) {
        for(VendorEntity vendor : vendorList) {
            vendorRepository.save(vendor);
        }
    }
}

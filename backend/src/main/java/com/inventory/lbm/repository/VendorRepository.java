package com.inventory.lbm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.lbm.entity.VendorEntity;

public interface VendorRepository extends JpaRepository<VendorEntity, String>{

}

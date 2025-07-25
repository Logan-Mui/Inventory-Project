package com.inventory.lbm.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventory.lbm.entity.RequestEntity;
import com.inventory.lbm.model.OperationType;


@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, UUID>{
    Optional<RequestEntity> findByRequestId(UUID requestId);
    Page<RequestEntity> findByRequestType(Pageable pageable, OperationType requestType);
}

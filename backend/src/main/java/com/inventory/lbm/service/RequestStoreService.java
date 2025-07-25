package com.inventory.lbm.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.inventory.lbm.entity.RequestEntity;
import com.inventory.lbm.model.OperationType;

public interface RequestStoreService {
    
    public RequestEntity createRequest(UUID requestId, OperationType operation, String requestPayload);

    public Page<RequestEntity> getRequests(Pageable paginated);

    public Optional<RequestEntity> getRequestByID(UUID requestId);

    public Page<RequestEntity> getRequestsByType(Pageable pageable, OperationType type);

    public void storeResponse(UUID requestId, String responsePayload);
}

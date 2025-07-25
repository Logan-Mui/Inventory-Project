package com.inventory.lbm.service.impl.storage;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.inventory.lbm.entity.RequestEntity;
import com.inventory.lbm.model.OperationType;
import com.inventory.lbm.repository.RequestRepository;
import com.inventory.lbm.service.RequestStoreService;

@Service
public class RequestStoreServiceImpl implements RequestStoreService{

    private final RequestRepository requestRepository;

    public RequestStoreServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public RequestEntity createRequest(UUID requestId, OperationType operation, String requestPayload) {
        RequestEntity request = new RequestEntity(requestId, operation, requestPayload);
        requestRepository.save(request);
        return request;
    }

    @Override
    public Page<RequestEntity> getRequests(Pageable pageable) {
        return requestRepository.findAll(pageable);
    }

    @Override
    public Optional<RequestEntity> getRequestByID(UUID requestID) {
        return requestRepository.findByRequestId(requestID);
    }

    @Override
    public Page<RequestEntity> getRequestsByType(Pageable pageable, OperationType type) {
        return requestRepository.findByRequestType(pageable, type);
    }

    @Override
    public void storeResponse(UUID requestId, String responsePayload) {
        requestRepository.findByRequestId(requestId).ifPresent((RequestEntity req) -> {
            req.setResponsePayload(responsePayload);
            req.setStatus("Completed");
            req.setCompletedAt(Instant.now());
            requestRepository.save(req);
        });
    }
}

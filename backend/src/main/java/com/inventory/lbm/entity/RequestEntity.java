package com.inventory.lbm.entity;

import java.time.Instant;
import java.util.UUID;

import com.inventory.lbm.model.OperationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "requests")
public class RequestEntity {

    @Id
    private UUID requestId;

    private OperationType requestType;

    @Column(columnDefinition = "TEXT")
    private String requestPayload;

    @Column(columnDefinition = "TEXT")
    private String responsePayload;

    private String status; // e.g., PENDING, SENT, COMPLETED, FAILED

    private Instant createdAt;

    private Instant completedAt;

    public RequestEntity() {}

    public RequestEntity(UUID requestId, OperationType requestType, String requestPayload) {
        this.requestId = requestId;
        this.requestType = requestType;
        this.requestPayload = requestPayload;
        this.status = "PENDING";
        this.createdAt = Instant.now();
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public OperationType getRequestType() {
        return requestType;
    }

    public void setRequestType(OperationType requestType) {
        this.requestType = requestType;
    }

    public String getRequestPayload() {
        return requestPayload;
    }

    public void setRequestPayload(String requestPayload) {
        this.requestPayload = requestPayload;
    }

    public String getResponsePayload() {
        return responsePayload;
    }

    public void setResponsePayload(String responsePayload) {
        this.responsePayload = responsePayload;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Instant completedAt) {
        this.completedAt = completedAt;
    }

    
}


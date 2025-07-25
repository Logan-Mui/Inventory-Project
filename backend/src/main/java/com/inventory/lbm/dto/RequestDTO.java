package com.inventory.lbm.dto;

import java.util.UUID;

import com.inventory.lbm.model.OperationType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class RequestDTO {
    private UUID requestId;
    private OperationType requestType;
    private String status;
}
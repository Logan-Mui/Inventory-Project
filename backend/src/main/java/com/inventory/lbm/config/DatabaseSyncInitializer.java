package com.inventory.lbm.config;

import java.util.UUID;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.inventory.lbm.entity.RequestEntity;
import com.inventory.lbm.model.OperationType;
import com.inventory.lbm.parser.RequestParser;
import com.inventory.lbm.service.QBWCService;
import com.inventory.lbm.service.RequestStoreService;

@Component
public class DatabaseSyncInitializer {
    
    private final RequestStoreService requestStoreService;

    private final QBWCService qbwcService;
    
    public DatabaseSyncInitializer(RequestStoreService requestStoreService, QBWCService qbwcService) {
        this.requestStoreService = requestStoreService;
        this.qbwcService = qbwcService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        initializeVendor();
        initializeInventory();
    }

    private void initializeInventory() {
        UUID requestID = UUID.randomUUID();

        String qbxmlRequest = RequestParser.getallToQBXML(requestID);

        RequestEntity request = requestStoreService.createRequest(requestID, OperationType.GET_ALL, qbxmlRequest);

        qbwcService.enqueue(request);
    }

    private void initializeVendor() {
        UUID requestID = UUID.randomUUID();

        String qbxmlRequest = RequestParser.getVendorsToQBXML(requestID);

        RequestEntity request = requestStoreService.createRequest(requestID, OperationType.UPDATE_VENDORS, qbxmlRequest);

        qbwcService.enqueue(request);
    }
}

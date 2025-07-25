package com.inventory.lbm.scheduled;

import java.util.UUID;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.inventory.lbm.entity.RequestEntity;
import com.inventory.lbm.model.OperationType;
import com.inventory.lbm.parser.RequestParser;
import com.inventory.lbm.service.QBWCService;
import com.inventory.lbm.service.RequestStoreService;
import com.inventory.lbm.service.impl.qbwc.QBWCServiceImpl;

@Component
public class SyncScheduler {

    private final QBWCService qbwcService;
    private final RequestStoreService requestStoreService;

    public SyncScheduler(RequestStoreService requestStoreService, QBWCServiceImpl qbwcService) {
        this.requestStoreService = requestStoreService;
        this.qbwcService = qbwcService;
    }

    @Scheduled(cron = "0 0 1 * * ?")  // Runs daily at 1:00 AM
    public void scheduleSync() {
        System.out.println("Starting Vendor sync job...");
        // Your logic to queue VendorQueryRq
        queueInventorySync();
        queueVendorSync();
    }

    private void queueVendorSync() {
        UUID requestId = UUID.randomUUID();

        String qbxmlRequest = RequestParser.getVendorsToQBXML(requestId);

        RequestEntity request = requestStoreService.createRequest(requestId, OperationType.UPDATE_VENDORS, qbxmlRequest);
        qbwcService.enqueue(request);
    }

    private void queueInventorySync() {
        UUID requestId = UUID.randomUUID();

        String qbxmlRequest = RequestParser.getallToQBXML(requestId);

        RequestEntity request = requestStoreService.createRequest(requestId, OperationType.GET_ALL, qbxmlRequest);
        qbwcService.enqueue(request);
    }
}


package com.inventory.lbm.service.impl.qbwc;

import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.inventory.lbm.entity.RequestEntity;
import com.inventory.lbm.model.InventoryResponse;
import com.inventory.lbm.model.OperationType;
import com.inventory.lbm.model.VendorResponse;
import com.inventory.lbm.parser.DispatchParser;
import com.inventory.lbm.parser.InventoryResponseParser;
import com.inventory.lbm.parser.VendorResponseParser;
import com.inventory.lbm.service.InventoryStoreService;
import com.inventory.lbm.service.QBWCService;
import com.inventory.lbm.service.RequestStoreService;
import com.inventory.lbm.service.VendorStoreService;
import com.inventory.lbm.service.WebSocketService;

import generated.ArrayOfString;

@Service
public class QBWCServiceImpl implements QBWCService{

    private final Queue<RequestEntity> requestQueue = new ConcurrentLinkedQueue<>();
    private final RequestStoreService requestStorageService;
    private final InventoryStoreService inventoryStoreService;
    private final VendorStoreService vendorStoreService;
    private final WebSocketService webSocketService;

    private final InventoryResponseParser inventoryResponseParser;
    private final VendorResponseParser vendorResponseParser;

    private static final Logger logger = LoggerFactory.getLogger(QBWCServiceImpl.class);

    public QBWCServiceImpl(RequestStoreService requestStorageService, InventoryStoreService inventoryStoreService, VendorStoreService vendorStoreService, WebSocketService webSocketService, InventoryResponseParser inventoryResponseParser, VendorResponseParser vendorResponseParser) {
        this.requestStorageService = requestStorageService;
        this.inventoryStoreService = inventoryStoreService;
        this.vendorStoreService = vendorStoreService;
        this.webSocketService = webSocketService;

        this.inventoryResponseParser = inventoryResponseParser;
        this.vendorResponseParser = vendorResponseParser;
    }

    @Override 
    public void enqueue(RequestEntity request) {
        requestQueue.add(request);
    }

    @Override
    public RequestEntity dequeue() {
        if(requestQueue.peek()!=null) {
            return requestQueue.poll();
        }
        else {
            return null;
        }
    }

    @Override
    public ArrayOfString authenticate(String username, String password) {
        ArrayOfString authenticate_result = new ArrayOfString();
        List<String> array = authenticate_result.getString();
        if(System.getenv("QUICKBOOK_USERNAME").equals(username) && System.getenv("QUICKBOOK_PASSWORD").equals(password)) {
            String randomTicket = UUID.randomUUID().toString();
            array.add(randomTicket);
        }
        else {
            array.add("NONE");
        }
        array.add("");
        array.add("");
        array.add("");
        return authenticate_result;
    }

    @Override
    public String getClientVersion(String version) {
        return "";
    }

    @Override
    public String closeConnection(String ticket) {
        return("ticket transaction: "+ticket+" closed.");
    }

    @Override
    public String connectionError(String ticket, String hresult, String message) {
        return "done";
    }

    @Override
    public String getLastError(String ticket) {
        return "QBWC is having an issue right now";
    }

    @Override
    public int receiveResponseXML(String ticket, String receive_response, String hresult, String message) throws Exception {
        
        if(hresult.isEmpty()) {
            UUID requestID = DispatchParser.fromQBXML(receive_response);

            Optional<RequestEntity> possibleRequest = requestStorageService.getRequestByID(requestID);

            requestStorageService.storeResponse(requestID, receive_response);

            possibleRequest.ifPresent(request -> {
                    if(request.getRequestType() != OperationType.UPDATE_VENDORS) {
                        try {
                            InventoryResponse response = inventoryResponseParser.fromQBXML(receive_response);
                            inventoryStoreService.storeResponse(response.getItemList());
                            logger.info("logged into inventory repository!");
                        }
                        catch (Exception ex) {
                            logger.info("Shit it broke on inventory");
                        }
                    }
                    else {
                        try {
                            VendorResponse response = vendorResponseParser.fromQBXML(receive_response);
                            vendorStoreService.storeResponse(response.getVendorList());
                            logger.info("logged into vendor repository!");
                        }
                        catch (Exception ex) {
                            logger.info("Shit it broke on vendor");
                        }
                    }
            });
            return 100;
        }
        else {
            return -101;
        }
    }

    @Override
    public String sendRequestXML(String ticket, String strHCPResponse, String strCompanyFilename, String qbXMLCountry, int qbXMLMajorVers, int qbXMLMinorVers) {
        RequestEntity nextRequest = dequeue();
        
        if(nextRequest!=null) {
            return nextRequest.getRequestPayload();
        }
        else {
            return "";
        }
    }

    @Override
    public String serverVersion() {
        return "Apache-Tomcat/10.1.41";
    }
}

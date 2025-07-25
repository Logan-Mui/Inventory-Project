package com.inventory.lbm.controller.qbwc;

import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.lbm.dto.RequestDTO;
import com.inventory.lbm.entity.RequestEntity;
import com.inventory.lbm.model.OperationType;
import com.inventory.lbm.parser.RequestParser;
import com.inventory.lbm.service.QBWCService;
import com.inventory.lbm.service.RequestStoreService;
import com.inventory.lbm.service.impl.qbwc.QBWCServiceImpl;



@RestController
@RequestMapping("/api/quickbooks")
public class QuickBookController {

    private static final Logger logger = LoggerFactory.getLogger(QuickBookController.class);

    private final QBWCService qbwcService;

    private final RequestStoreService requestStoreService;
    
    public QuickBookController(QBWCServiceImpl qbwcService, RequestStoreService requestStoreService) {
        this.qbwcService = qbwcService;
        this.requestStoreService = requestStoreService;
    }

    @GetMapping("/")
    public ResponseEntity<RequestDTO> getInventory() throws DatatypeConfigurationException {

        logger.info("QBC - Recieved a get all inventory request");

        UUID requestId = UUID.randomUUID();
        
        String qbxmlRequest = RequestParser.getallToQBXML(requestId);

        RequestEntity request = requestStoreService.createRequest(requestId, OperationType.GET_ALL, qbxmlRequest);

        qbwcService.enqueue(request);

        return ResponseEntity.ok(new RequestDTO(requestId, OperationType.GET_ALL, "Queued"));
    }

    @PutMapping("/id/{id}/quantity/{quantityDifference}")
    public ResponseEntity<RequestDTO> updateQuantity(@PathVariable String id, @PathVariable int quantityDifference) {

        logger.info("QBC - Recieved update quantity request");

        UUID requestId = UUID.randomUUID();

        String qbxmlRequest = RequestParser.updateQtyToQBXML(requestId, id, quantityDifference);
        
        RequestEntity request = requestStoreService.createRequest(requestId, OperationType.UPDATE_QUANTITY, qbxmlRequest);

        qbwcService.enqueue(request);

        return ResponseEntity.ok(new RequestDTO(requestId, OperationType.UPDATE_QUANTITY, "Request Successfully queued"));
    }

    @PostMapping("/name/{name}/vendor/{vendorID}")
    public ResponseEntity<RequestDTO> createItem(@PathVariable String name, @PathVariable String vendorID) {
    
        logger.info("QBC - Recieved create inventory request");
        
        UUID requestId = UUID.randomUUID();

        String qbxmlRequest = RequestParser.addToQBXML(requestId, name);

        RequestEntity request = requestStoreService.createRequest(requestId, OperationType.CREATE_ITEM, qbxmlRequest);

        qbwcService.enqueue(request);

        return ResponseEntity.ok(new RequestDTO(requestId, OperationType.CREATE_ITEM, "Request Successfully queued"));
    }
}

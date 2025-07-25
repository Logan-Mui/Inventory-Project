package com.inventory.lbm.service.impl;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.inventory.lbm.model.InventoryResponse;
import com.inventory.lbm.model.VendorResponse;
import com.inventory.lbm.service.WebSocketService;

@Service
public class WebSocketServiceImpl implements WebSocketService{

    private final SimpMessagingTemplate messagingTemplate;
    
    public  WebSocketServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void sendInventoryUpdate(InventoryResponse response) {
        messagingTemplate.convertAndSend("/topic/inventory",response);
    }

    @Override
    public void sendVendorUpdate(VendorResponse response) {
        messagingTemplate.convertAndSend("/topic/vendor",response);
    }
}

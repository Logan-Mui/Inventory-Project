package com.inventory.lbm.service;

import com.inventory.lbm.model.InventoryResponse;
import com.inventory.lbm.model.VendorResponse;

public interface WebSocketService {
    void sendInventoryUpdate(InventoryResponse response);
    void sendVendorUpdate(VendorResponse response);
}

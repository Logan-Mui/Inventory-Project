package com.inventory.lbm.model;

import java.util.List;
import java.util.UUID;

import com.inventory.lbm.entity.InventoryEntity;



public class InventoryResponse {

    private UUID responseID;
    private List<InventoryEntity> itemList;


    public InventoryResponse(UUID responseID, List<InventoryEntity> itemList) {
        this.responseID = responseID;
        this.itemList = itemList;
    }

    public UUID getResponseID() {
        return responseID;
    }

    public void setResponseID(UUID responseID) {
        this.responseID = responseID;
    }

    public List<InventoryEntity> getItemList() {
        return itemList;
    }

    public void setItemList(List<InventoryEntity> itemList) {
        this.itemList = itemList;
    }
}

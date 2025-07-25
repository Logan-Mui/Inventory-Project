package com.inventory.lbm.model;

import java.util.List;
import java.util.UUID;

import com.inventory.lbm.entity.VendorEntity;



public class VendorResponse {

    private UUID responseID;
    private List<VendorEntity> vendorList;


    public VendorResponse(UUID responseID, List<VendorEntity> vendorList) {
        this.responseID = responseID;
        this.vendorList = vendorList;
    }

    public UUID getResponseID() {
        return responseID;
    }

    public void setResponseID(UUID responseID) {
        this.responseID = responseID;
    }

    public List<VendorEntity> getVendorList() {
        return vendorList;
    }

    public void setItemList(List<VendorEntity> vendorList) {
        this.vendorList = vendorList;
    }
    
}

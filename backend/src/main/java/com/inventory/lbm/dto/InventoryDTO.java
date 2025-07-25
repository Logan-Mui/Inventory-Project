package com.inventory.lbm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class InventoryDTO {
    private String listId;
    private String name;
    private int quantity;
    private VendorDTO vendorRef;
}

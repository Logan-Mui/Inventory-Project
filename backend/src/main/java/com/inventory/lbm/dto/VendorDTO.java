package com.inventory.lbm.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class VendorDTO {
    private String listID;
    private String companyName;
    private boolean active;
}

package com.inventory.lbm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory_items")

public class InventoryEntity {
    @Id
    private String listId;

    private String name;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "vendorId")
    private VendorEntity vendorRef;

    private String editSequence;

    public InventoryEntity(){}

    public InventoryEntity(String listId, String name, int quantity, VendorEntity vendorRef, String editSequence) {
        this.listId = listId;
        this.name = name;
        this.quantity = quantity;
        this.vendorRef = vendorRef;
        this.editSequence = editSequence;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public VendorEntity getVendorRef() {
        return vendorRef;
    }

    public void setVendorRef(VendorEntity vendorRef) {
        this.vendorRef = vendorRef;
    }

    public String getEditSequence() {
        return editSequence;
    }

    public void setEditSequence(String editSequence) {
        this.editSequence = editSequence;
    }
}

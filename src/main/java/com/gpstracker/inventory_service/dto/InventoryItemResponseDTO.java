package com.gpstracker.inventory_service.dto; // THIS SHOULD MATCH YOUR PACKAGE

import com.gpstracker.inventory_service.model.InventoryItemStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemResponseDTO {

    private Long id; // ID of the inventory item itself

    // Product Information (denormalized for easy display)
    private Long productId;
    private String productName;
    private String productSku;
    private String productCategory; // String representation of ProductCategory enum

    // Unique Identifiers
    private String imei;
    private String iccid;
    private String serialNumber;

    private InventoryItemStatus status;
    private String location;
    private LocalDate receivedDate;
    private String notes;

    // Optional: If you implement supplier/PO linking
    // private Long supplierId;
    // private String supplierName;
    // private Long purchaseOrderId;
    // private String purchaseOrderNumber;

    // Optional: If you implement SIM-Tracker assignment
    // private Long assignedTrackerItemId; // ID of the tracker InventoryItem this SIM is assigned to
    // private String assignedTrackerImei; // IMEI of that tracker

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
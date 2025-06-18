package com.gpstracker.inventory_service.model; // Or your package name

public enum InventoryItemStatus {
    IN_STOCK,         // Available for sale or use
    ALLOCATED,        // Reserved for a sale, kit, or deployment but not yet shipped/used
    SOLD,             // Sold and shipped to a customer
    IN_USE,           // Actively deployed or in use (e.g., a SIM card in an active tracker)
    DEFECTIVE,        // Found to be faulty
    IN_REPAIR,        // Currently undergoing repair
    REPAIRED_IN_STOCK, // Repaired and back in stock, potentially as refurbished
    DISPOSED,         // Scrapped, lost, or otherwise removed from inventory permanently
    PENDING_RECEIPT   // Expected from a supplier but not yet physically received/inspected
}
package com.gpstracker.inventory_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory_items", indexes = { // Add indexes for frequently queried unique fields
        @Index(name = "idx_inventoryitem_imei", columnList = "imei", unique = true),
        @Index(name = "idx_inventoryitem_iccid", columnList = "iccid", unique = true),
        @Index(name = "idx_inventoryitem_serial_number", columnList = "serial_number", unique = true)
})
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Many InventoryItems can be of one Product type
    @JoinColumn(name = "product_id", nullable = false) // Foreign key column
    private Product product; // Reference to the Product entity

    // Unique identifiers for specific types of items
    @Column(length = 20, unique = true, nullable = true) // IMEI usually 15 digits, allow some buffer
    private String imei; // For GPS Trackers

    @Column(length = 22, unique = true, nullable = true) // ICCID usually 18-22 digits
    private String iccid; // For SIM Cards

    @Column(length = 100, unique = true, nullable = true)
    private String serialNumber; // For accessories or other items with serials

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InventoryItemStatus status;

    @Column(length = 100, nullable = true)
    private String location; // e.g., "Warehouse A, Shelf B2, Bin 3"

    private LocalDate receivedDate; // Date the item was physically received into inventory

    // Optional: Link to supplier if received directly from a specific supplier
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "supplier_id", nullable = true)
    // private Supplier supplier;

    // Optional: Link to the purchase order this item was received against
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "purchase_order_id", nullable = true)
    // private PurchaseOrder purchaseOrder;

    // Optional: If a SIM (InventoryItem) is assigned to a Tracker (another InventoryItem)
    // This creates a self-referencing relationship for pairing, handle with care.
    // @OneToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "assigned_tracker_item_id", nullable = true, unique = true)
    // private InventoryItem assignedTrackerItem; // Only applicable if this item is a SIM

    @Lob
    @Column(columnDefinition = "TEXT")
    private String notes; // Any additional notes about this specific item

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // --- Custom Logic / Helper methods (optional, can also be in service) ---
    // Example: Pre-persist or Pre-update logic to ensure only one unique ID is set
    @PrePersist
    @PreUpdate
    private void ensureCorrectIdentifier() {
        if (this.product != null) {
            ProductCategory category = this.product.getCategory();
            if (category == ProductCategory.GPS_TRACKER) {
                if (imei == null || imei.trim().isEmpty()) {
                    throw new IllegalStateException("IMEI is required for GPS_TRACKER products.");
                }
                iccid = null;
                serialNumber = null;
            } else if (category == ProductCategory.SIM_CARD) {
                if (iccid == null || iccid.trim().isEmpty()) {
                    throw new IllegalStateException("ICCID is required for SIM_CARD products.");
                }
                imei = null;
                serialNumber = null;
            } else if (category == ProductCategory.ACCESSORY) {
                // serialNumber might be optional or mandatory for accessories based on business rules
                imei = null;
                iccid = null;
            } else {
                // For BUNDLE or OTHER, unique identifiers might not be directly on InventoryItem
                // or might be handled differently.
                imei = null;
                iccid = null;
                serialNumber = null;
            }
        }

        // Ensure only one of imei, iccid, serialNumber is set if they are mutually exclusive based on category
        long count = java.util.stream.Stream.of(imei, iccid, serialNumber)
                .filter(s -> s != null && !s.trim().isEmpty())
                .count();
        // This check might be too simplistic if an item could legitimately have more than one.
        // Adjust based on your actual business rules.
        // For now, assuming an item is primarily identified by ONE of these based on category.
        // The logic above already nullifies others based on category.
    }
}
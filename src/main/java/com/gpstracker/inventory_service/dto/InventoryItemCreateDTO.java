package com.gpstracker.inventory_service.dto; // THIS SHOULD MATCH YOUR PACKAGE

import com.gpstracker.inventory_service.model.InventoryItemStatus; // Your status enum
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemCreateDTO {

    @NotNull(message = "Product ID cannot be null")
    private Long productId; // We'll link to an existing Product by its ID

    // At least one of these unique identifiers should be provided based on product category.
    // The service layer will validate this more thoroughly.
    @Size(max = 20, message = "IMEI cannot exceed 20 characters")
    // Basic IMEI pattern - can be more specific if needed
    @Pattern(regexp = "^[0-9]{14,15}$", message = "IMEI must be 14 or 15 digits", groups = ImeiValidation.class)
    private String imei;

    @Size(max = 22, message = "ICCID cannot exceed 22 characters")
    // Basic ICCID pattern
    @Pattern(regexp = "^[0-9]{18,22}$", message = "ICCID must be 18 to 22 digits", groups = IccidValidation.class)
    private String iccid;

    @Size(max = 100, message = "Serial number cannot exceed 100 characters")
    private String serialNumber;

    @NotNull(message = "Status cannot be null")
    private InventoryItemStatus status = InventoryItemStatus.IN_STOCK; // Default to IN_STOCK

    @Size(max = 100, message = "Location cannot exceed 100 characters")
    private String location;

    private LocalDate receivedDate; // Optional, can default to today in service

    private String notes;

    // Validation groups (interfaces) for conditional validation
    public interface ImeiValidation {}
    public interface IccidValidation {}

    // ---
    // For bulk creation, you might have another DTO like:
    // public class BulkInventoryItemCreateDTO {
    //     @NotNull private Long productId;
    //     @NotNull private InventoryItemStatus status;
    //     private String location;
    //     private LocalDate receivedDate;
    //     @NotEmpty private List<String> imeis; // or iccidList or serialNumberList
    // }
    // We'll stick to single item creation DTO for now for simplicity.
    // ---
}
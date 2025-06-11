package com.gpstracker.inventory_service.dto; // THIS SHOULD MATCH YOUR PACKAGE

import com.gpstracker.inventory_service.model.ProductCategory; // Import ProductCategory enum
import jakarta.validation.constraints.*; // For validation annotations
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data // Lombok: Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok: Generates no-args constructor
@AllArgsConstructor // Lombok: Generates all-args constructor
public class ProductDTO {

    private Long id; // Will be populated for responses, null for creation requests

    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    private String name;

    @NotBlank(message = "SKU cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9-]{3,50}$", message = "SKU must be 3-50 characters and can only contain letters, numbers, and hyphens")
    private String sku;

    private String description; // Optional, so no @NotBlank

    @NotNull(message = "Category cannot be null")
    private ProductCategory category;

    @NotNull(message = "Cost price cannot be null")
    @DecimalMin(value = "0.01", message = "Cost price must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Cost price format is invalid (e.g., 12345678.99)")
    private BigDecimal costPrice;

    @NotNull(message = "Sale price cannot be null")
    @DecimalMin(value = "0.01", message = "Sale price must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Sale price format is invalid (e.g., 12345678.99)")
    private BigDecimal salePrice;

    // These fields are typically for responses, not inputs for creation/update
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
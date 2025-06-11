package com.gpstracker.inventory_service.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data // Lombok: Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok: Generates no-args constructor (required by JPA)
@AllArgsConstructor // Lombok: Generates all-args constructor
@Entity // Marks this class as a JPA entity (a table in the database)
@Table(name = "products") // Specifies the database table name
public class Product {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configures auto-increment for the ID
    private Long id;

    @Column(nullable = false, unique = true, length = 100) // Database column constraints
    private String name; // e.g., "Tracker Model X", "SIM Card Type A"

    @Column(nullable = false, unique = true, length = 50)
    private String sku; // Stock Keeping Unit, e.g., "GPS-XT-001"

    @Lob // Indicates a Large Object, suitable for longer text.
    @Column(columnDefinition = "TEXT") // More specific for MySQL text type
    private String description;

    @Enumerated(EnumType.STRING) // Stores the enum as a String in the DB (e.g., "GPS_TRACKER")
    @Column(nullable = false, length = 20)
    private ProductCategory category; // Uses the ProductCategory enum we created

    @Column(nullable = false, precision = 10, scale = 2) // For decimal numbers like currency
    private BigDecimal costPrice;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salePrice;

    @CreationTimestamp // Automatically sets the value when the entity is first created
    @Column(nullable = false, updatable = false) // Cannot be null, and not updated after creation
    private LocalDateTime createdAt;

    @UpdateTimestamp // Automatically sets the value when the entity is updated
    @Column(nullable = false) // Cannot be null
    private LocalDateTime updatedAt;
}
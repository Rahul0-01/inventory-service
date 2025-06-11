package com.gpstracker.inventory_service.repository;


import com.gpstracker.inventory_service.model.Product; // Import your Product entity
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Optional for Spring Boot 2.x+, but good practice for clarity
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Spring Data JPA will automatically generate the implementation for these methods
    // based on their names.

    // Find a product by its SKU
    Optional<Product> findBySku(String sku);

    // Find a product by its name
    Optional<Product> findByName(String name);

    // Check if a product exists by its SKU (more efficient than findBySku().isPresent())
    boolean existsBySku(String sku);

    // Check if a product exists by its name
    boolean existsByName(String name);
}

package com.gpstracker.inventory_service.repository; // THIS SHOULD MATCH YOUR PACKAGE

import com.gpstracker.inventory_service.model.InventoryItem;
import com.gpstracker.inventory_service.model.Product; // For finding by product
import com.gpstracker.inventory_service.model.InventoryItemStatus; // For finding by status
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // For more complex queries
import org.springframework.data.repository.query.Param; // For named parameters in queries
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    // --- Finders for unique identifiers ---
    Optional<InventoryItem> findByImei(String imei);
    Optional<InventoryItem> findByIccid(String iccid);
    Optional<InventoryItem> findBySerialNumber(String serialNumber);

    // --- Existence checks for unique identifiers (more efficient than findBy...().isPresent()) ---
    boolean existsByImei(String imei);
    boolean existsByIccid(String iccid);
    boolean existsBySerialNumber(String serialNumber);

    // --- Finders for common queries ---
    List<InventoryItem> findByProduct(Product product);
    List<InventoryItem> findByStatus(InventoryItemStatus status);
    List<InventoryItem> findByProductAndStatus(Product product, InventoryItemStatus status);

    // Example of a more complex query using @Query (e.g., to count stock for a product)
    // This is useful for getting aggregated data without fetching all entities.
    @Query("SELECT COUNT(i) FROM InventoryItem i WHERE i.product = :product AND i.status = :status")
    long countByProductAndStatus(@Param("product") Product product, @Param("status") InventoryItemStatus status);

    // You can add more specific finders as needed, e.g.,
    // List<InventoryItem> findByLocation(String location);
    // List<InventoryItem> findByReceivedDateBetween(LocalDate startDate, LocalDate endDate);
}
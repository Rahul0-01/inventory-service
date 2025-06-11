package com.gpstracker.inventory_service.service; // THIS SHOULD MATCH YOUR PACKAGE

import com.gpstracker.inventory_service.dto.ProductDTO; // Import your DTO
import java.util.List;

public interface ProductService {

    /**
     * Creates a new product.
     * @param productDTO The DTO containing product details.
     * @return The DTO of the created product.
     */
    ProductDTO createProduct(ProductDTO productDTO);

    /**
     * Retrieves a product by its ID.
     * @param productId The ID of the product.
     * @return The DTO of the found product.
     * @throws com.gpstracker.inventory_service.exception.ResourceNotFoundException if product not found.
     */
    ProductDTO getProductById(Long productId);

    /**
     * Retrieves a product by its SKU.
     * @param sku The SKU of the product.
     * @return The DTO of the found product.
     * @throws com.gpstracker.inventory_service.exception.ResourceNotFoundException if product not found.
     */
    ProductDTO getProductBySku(String sku);

    /**
     * Retrieves all products.
     * @return A list of product DTOs.
     */
    List<ProductDTO> getAllProducts();

    /**
     * Updates an existing product.
     * @param productId The ID of the product to update.
     * @param productDTO The DTO containing updated product details.
     * @return The DTO of the updated product.
     * @throws com.gpstracker.inventory_service.exception.ResourceNotFoundException if product not found.
     */
    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    /**
     * Deletes a product by its ID.
     * @param productId The ID of the product to delete.
     * @throws com.gpstracker.inventory_service.exception.ResourceNotFoundException if product not found.
     */
    void deleteProduct(Long productId);
}
package com.gpstracker.inventory_service.controller;

import com.gpstracker.inventory_service.dto.ProductDTO;
import com.gpstracker.inventory_service.service.ProductService;
import jakarta.validation.Valid; // For validating request bodies
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marks this class as a REST controller, combining @Controller and @ResponseBody
@RequestMapping("/api/v1/products") // Base path for all endpoints in this controller
public class ProductController {

    private final ProductService productService;

    // Constructor Injection for the service
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // POST /api/v1/products - Create a new product
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        // @Valid: Triggers validation on ProductDTO based on its annotations
        // @RequestBody: Maps the HTTP request body (JSON) to the ProductDTO object
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED); // 201 Created
    }

    // GET /api/v1/products/{id} - Get a product by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        // @PathVariable: Extracts the 'id' value from the URL path
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.ok(productDTO); // 200 OK with the product DTO
    }

    // GET /api/v1/products/sku/{sku} - Get a product by its SKU
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductDTO> getProductBySku(@PathVariable String sku) {
        ProductDTO productDTO = productService.getProductBySku(sku);
        return ResponseEntity.ok(productDTO);
    }

    // GET /api/v1/products - Get all products
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // PUT /api/v1/products/{id} - Update an existing product
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    // DELETE /api/v1/products/{id} - Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
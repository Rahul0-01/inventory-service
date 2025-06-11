package com.gpstracker.inventory_service.service; // THIS SHOULD MATCH YOUR PACKAGE

import com.gpstracker.inventory_service.dto.ProductDTO;
import com.gpstracker.inventory_service.exception.ResourceConflictException;
import com.gpstracker.inventory_service.exception.ResourceNotFoundException;
import com.gpstracker.inventory_service.model.Product;
import com.gpstracker.inventory_service.repository.ProductRepository;
import org.springframework.beans.BeanUtils; // For simple DTO-Entity mapping
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // For database transaction management

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service // Marks this class as a Spring service component
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    // Constructor injection is preferred for dependencies
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional // Ensures this operation is a single database transaction
    public ProductDTO createProduct(ProductDTO productDTO) {
        // Check for conflicts before attempting to save
        if (productRepository.existsBySku(productDTO.getSku())) {
            throw new ResourceConflictException("Product", "SKU", productDTO.getSku());
        }
        if (productRepository.existsByName(productDTO.getName())) {
            throw new ResourceConflictException("Product", "name", productDTO.getName());
        }

        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    @Override
    @Transactional(readOnly = true) // Read-only transaction, can be optimized by DB
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        return convertToDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductBySku(String sku) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "SKU", sku));
        return convertToDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO) // Method reference for cleaner code
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        // Check for SKU conflict only if SKU is being changed AND the new SKU belongs to another product
        if (!existingProduct.getSku().equals(productDTO.getSku())) {
            Optional<Product> productByNewSku = productRepository.findBySku(productDTO.getSku());
            if (productByNewSku.isPresent() && !productByNewSku.get().getId().equals(productId)) {
                throw new ResourceConflictException("Product", "SKU", productDTO.getSku());
            }
        }

        // Check for Name conflict only if Name is being changed AND the new Name belongs to another product
        if (!existingProduct.getName().equals(productDTO.getName())) {
            Optional<Product> productByNewName = productRepository.findByName(productDTO.getName());
            if (productByNewName.isPresent() && !productByNewName.get().getId().equals(productId)) {
                throw new ResourceConflictException("Product", "name", productDTO.getName());
            }
        }

        // Update fields of the existing product
        // BeanUtils.copyProperties can be used, but be careful with nulls if DTO fields can be null for updates
        // For more control, set fields manually:
        existingProduct.setName(productDTO.getName());
        existingProduct.setSku(productDTO.getSku());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setCategory(productDTO.getCategory());
        existingProduct.setCostPrice(productDTO.getCostPrice());
        existingProduct.setSalePrice(productDTO.getSalePrice());
        // createdAt and id are not updated. updatedAt will be handled by @UpdateTimestamp

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product", "id", productId);
        }
        // Future consideration: Add logic here to check if the product is currently
        // part of any active inventory items or sales orders before allowing deletion.
        // For now, we'll allow direct deletion.
        productRepository.deleteById(productId);
    }

    // --- Helper Methods for DTO/Entity Conversion ---

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO); // Simple mapping
        return productDTO;
    }

    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product, "id", "createdAt", "updatedAt"); // Exclude fields not set during creation
        return product;
    }
}
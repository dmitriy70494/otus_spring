package com.example.dbisolation.service;

import com.example.dbisolation.model.ProductSerializable;
import com.example.dbisolation.repository.ProductSerializableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SerializableProductService {

    private final ProductSerializableRepository productRepository;

    @Transactional
    public ProductSerializable createProduct(ProductSerializable product) {
        return productRepository.save(product);
    }

    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public List<ProductSerializable> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true, isolation = Isolation.SERIALIZABLE)
    public Optional<ProductSerializable> getProductById(Long id) {
        // Simulate some work
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return productRepository.findById(id);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ProductSerializable updateProduct(Long id, ProductSerializable productDetails) {
        ProductSerializable product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setName(productDetails.getName());
        product.setQuantity(productDetails.getQuantity());
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

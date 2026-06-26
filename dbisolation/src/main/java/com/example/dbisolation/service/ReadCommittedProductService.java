package com.example.dbisolation.service;

import com.example.dbisolation.model.ProductReadCommitted;
import com.example.dbisolation.repository.ProductReadCommittedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReadCommittedProductService {

    private final ProductReadCommittedRepository productRepository;

    @Transactional
    public ProductReadCommitted createProduct(ProductReadCommitted product) {
        return productRepository.save(product);
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<ProductReadCommitted> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public Optional<ProductReadCommitted> getProductById(Long id) {
        // Simulate some work
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return productRepository.findById(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ProductReadCommitted updateProduct(Long id, ProductReadCommitted productDetails) {
        ProductReadCommitted product = productRepository.findById(id)
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

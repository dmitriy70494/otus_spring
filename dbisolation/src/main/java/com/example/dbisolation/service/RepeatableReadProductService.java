package com.example.dbisolation.service;

import com.example.dbisolation.model.ProductRepeatableRead;
import com.example.dbisolation.repository.ProductRepeatableReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepeatableReadProductService {

    private final ProductRepeatableReadRepository productRepository;

    @Transactional
    public ProductRepeatableRead createProduct(ProductRepeatableRead product) {
        return productRepository.save(product);
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public List<ProductRepeatableRead> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public Optional<ProductRepeatableRead> getProductById(Long id) {
        // Simulate some work
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return productRepository.findById(id);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ProductRepeatableRead updateProduct(Long id, ProductRepeatableRead productDetails) {
        ProductRepeatableRead product = productRepository.findById(id)
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

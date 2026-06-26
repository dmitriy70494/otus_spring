package com.example.dbisolation.service;

import com.example.dbisolation.model.ProductOptimistic;
import com.example.dbisolation.repository.ProductOptimisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OptimisticProductService {

    private final ProductOptimisticRepository productRepository;

    @Transactional
    public ProductOptimistic createProduct(ProductOptimistic product) {
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<ProductOptimistic> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ProductOptimistic> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public ProductOptimistic updateProduct(Long id, ProductOptimistic productDetails) {
        ProductOptimistic product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setName(productDetails.getName());
        product.setQuantity(productDetails.getQuantity());
        // The version is handled automatically by JPA
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

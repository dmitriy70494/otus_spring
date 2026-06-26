package com.example.dbisolation.service;

import com.example.dbisolation.model.ProductPessimistic;
import com.example.dbisolation.repository.ProductPessimisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PessimisticProductService {

    private final ProductPessimisticRepository productRepository;

    @Transactional
    public ProductPessimistic createProduct(ProductPessimistic product) {
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<ProductPessimistic> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Optional<ProductPessimistic> getProductByIdWithReadLock(Long id) {
        return productRepository.findByIdWithPessimisticReadLock(id);
    }

    @Transactional
    public Optional<ProductPessimistic> getProductByIdWithWriteLock(Long id) {
        return productRepository.findByIdWithPessimisticWriteLock(id);
    }

    @Transactional
    public ProductPessimistic updateProduct(Long id, ProductPessimistic productDetails) {
        ProductPessimistic product = productRepository.findById(id)
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

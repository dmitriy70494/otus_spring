package com.example.dbisolation.controller;

import com.example.dbisolation.model.ProductPessimistic;
import com.example.dbisolation.service.PessimisticProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessimistic-read/products")
@RequiredArgsConstructor
public class PessimisticReadLockingController {

    private final PessimisticProductService productService;

    @PostMapping
    public ProductPessimistic createProduct(@RequestBody ProductPessimistic product) {
        return productService.createProduct(product);
    }

    @GetMapping
    public List<ProductPessimistic> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductPessimistic> getProductById(@PathVariable Long id) {
        return productService.getProductByIdWithReadLock(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductPessimistic> updateProduct(@PathVariable Long id, @RequestBody ProductPessimistic productDetails) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, productDetails));
        } catch (Exception e) {
            // Or more specific exception handling
            return ResponseEntity.status(409).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}

package com.example.dbisolation.controller;

import com.example.dbisolation.model.ProductReadCommitted;
import com.example.dbisolation.service.ReadCommittedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/isolation/read-committed/products")
@RequiredArgsConstructor
public class ReadCommittedController {

    private final ReadCommittedProductService productService;

    @PostMapping
    public ProductReadCommitted createProduct(@RequestBody ProductReadCommitted product) {
        return productService.createProduct(product);
    }

    @GetMapping
    public List<ProductReadCommitted> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductReadCommitted> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductReadCommitted> updateProduct(@PathVariable Long id, @RequestBody ProductReadCommitted productDetails) {
        try {
            return ResponseEntity.ok(productService.updateProduct(id, productDetails));
        } catch (Exception e) {
            return ResponseEntity.status(409).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}

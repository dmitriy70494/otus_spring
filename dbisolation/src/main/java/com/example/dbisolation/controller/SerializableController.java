package com.example.dbisolation.controller;

import com.example.dbisolation.model.ProductSerializable;
import com.example.dbisolation.service.SerializableProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/isolation/serializable/products")
@RequiredArgsConstructor
public class SerializableController {

    private final SerializableProductService productService;

    @PostMapping
    public ProductSerializable createProduct(@RequestBody ProductSerializable product) {
        return productService.createProduct(product);
    }

    @GetMapping
    public List<ProductSerializable> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductSerializable> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductSerializable> updateProduct(@PathVariable Long id, @RequestBody ProductSerializable productDetails) {
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

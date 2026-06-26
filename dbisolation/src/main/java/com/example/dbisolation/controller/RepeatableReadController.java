package com.example.dbisolation.controller;

import com.example.dbisolation.model.ProductRepeatableRead;
import com.example.dbisolation.service.RepeatableReadProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/isolation/repeatable-read/products")
@RequiredArgsConstructor
public class RepeatableReadController {

    private final RepeatableReadProductService productService;

    @PostMapping
    public ProductRepeatableRead createProduct(@RequestBody ProductRepeatableRead product) {
        return productService.createProduct(product);
    }

    @GetMapping
    public List<ProductRepeatableRead> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductRepeatableRead> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductRepeatableRead> updateProduct(@PathVariable Long id, @RequestBody ProductRepeatableRead productDetails) {
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

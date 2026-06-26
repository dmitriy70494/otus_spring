package com.example.dbisolation.controller;

import com.example.dbisolation.model.ProductOptimistic;
import com.example.dbisolation.service.OptimisticProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/optimistic-locking/products")
@RequiredArgsConstructor
public class OptimisticLockingController {

    private final OptimisticProductService productService;

    @PostMapping
    public ProductOptimistic createProduct(@RequestBody ProductOptimistic product) {
        return productService.createProduct(product);
    }

    @GetMapping
    public List<ProductOptimistic> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOptimistic> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductOptimistic productDetails) {
        try {
            ProductOptimistic updatedProduct = productService.updateProduct(id, productDetails);
            return ResponseEntity.ok(updatedProduct);
        } catch (ObjectOptimisticLockingFailureException e) {
            return ResponseEntity.status(409).body("Conflict: The product was updated by another transaction.");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}

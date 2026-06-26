package com.example.dbisolation.repository;

import com.example.dbisolation.model.ProductOptimistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptimisticRepository extends JpaRepository<ProductOptimistic, Long> {
}

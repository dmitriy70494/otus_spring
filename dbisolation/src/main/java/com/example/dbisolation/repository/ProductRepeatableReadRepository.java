package com.example.dbisolation.repository;

import com.example.dbisolation.model.ProductRepeatableRead;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepeatableReadRepository extends JpaRepository<ProductRepeatableRead, Long> {
}

package com.example.dbisolation.repository;

import com.example.dbisolation.model.ProductSerializable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSerializableRepository extends JpaRepository<ProductSerializable, Long> {
}

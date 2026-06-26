package com.example.dbisolation.repository;

import com.example.dbisolation.model.ProductReadCommitted;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReadCommittedRepository extends JpaRepository<ProductReadCommitted, Long> {
}

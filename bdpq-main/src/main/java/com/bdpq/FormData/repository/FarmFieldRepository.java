package com.bdpq.FormData.repository;

import com.bdpq.FormData.model.FarmField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmFieldRepository extends JpaRepository<FarmField, Long> {
}

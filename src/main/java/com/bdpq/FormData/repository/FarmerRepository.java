package com.bdpq.FormData.repository;

import com.bdpq.FormData.model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FarmerRepository extends JpaRepository<Farmer, Integer> {
    Optional<Farmer> findByName(String name);
}

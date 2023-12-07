package com.bdpq.FormData.repository;

import com.bdpq.FormData.model.Machinery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineryRepository extends JpaRepository<Machinery, Long> {
}

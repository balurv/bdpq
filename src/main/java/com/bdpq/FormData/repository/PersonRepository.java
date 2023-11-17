package com.bdpq.FormData.repository;

import com.bdpq.FormData.model.Person;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository  extends JpaRepository<Person, Integer> {
    Optional<Person> findByName(String name);
}

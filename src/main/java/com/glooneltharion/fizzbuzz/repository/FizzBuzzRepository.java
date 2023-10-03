package com.glooneltharion.fizzbuzz.repository;

import com.glooneltharion.fizzbuzz.model.FizzBuzz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FizzBuzzRepository extends JpaRepository<FizzBuzz, Long> {
    Optional<FizzBuzz> findBySecretCode(String secretCode);
    void deleteBySecretCode(String secretCode);
}


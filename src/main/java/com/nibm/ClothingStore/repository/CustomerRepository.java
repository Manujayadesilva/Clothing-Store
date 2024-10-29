package com.nibm.ClothingStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nibm.ClothingStore.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
package com.example.nobs.mappings;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    
}

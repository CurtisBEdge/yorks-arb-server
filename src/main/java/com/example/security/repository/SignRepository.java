package com.example.security.repository;

import com.example.security.domain.Sign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignRepository extends JpaRepository<Sign, Integer> {

    boolean existsById(Integer id);


}

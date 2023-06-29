package com.example.security.repository;

import com.example.security.domain.SignImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignImageRepository extends JpaRepository<SignImage, Integer> {
}

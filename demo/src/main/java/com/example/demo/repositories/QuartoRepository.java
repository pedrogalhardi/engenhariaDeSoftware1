package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.Quarto;

public interface QuartoRepository extends JpaRepository<Quarto, Long> {
}

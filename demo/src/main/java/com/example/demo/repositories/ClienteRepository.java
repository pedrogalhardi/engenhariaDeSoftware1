package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}

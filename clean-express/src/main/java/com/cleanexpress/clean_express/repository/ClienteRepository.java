package com.cleanexpress.clean_express.repository;

import com.cleanexpress.clean_express.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Aquí Spring Data JPA nos regala automáticamente métodos como findAll(), save(), findById(), etc.
}
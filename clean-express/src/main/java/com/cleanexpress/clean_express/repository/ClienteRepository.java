package com.cleanexpress.clean_express.repository;

import com.cleanexpress.clean_express.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Búsqueda flexible por nombre o teléfono para evitar duplicados
    List<Cliente> findByNombreContainingIgnoreCaseOrTelefonoContaining(String nombre, String telefono);
    Optional<Cliente> findByTelefono(String telefono);
}
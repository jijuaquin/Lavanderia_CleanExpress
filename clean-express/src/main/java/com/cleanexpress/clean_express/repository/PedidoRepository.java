package com.cleanexpress.clean_express.repository;

import com.cleanexpress.clean_express.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Filtrar pedidos por estado (RF-02)
    List<Pedido> findByEstado(String estado);
}
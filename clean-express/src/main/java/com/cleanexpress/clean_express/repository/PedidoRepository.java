package com.cleanexpress.clean_express.repository;

import com.cleanexpress.clean_express.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Métodos personalizados para buscar pedidos por cliente o estado si los necesitamos luego
}
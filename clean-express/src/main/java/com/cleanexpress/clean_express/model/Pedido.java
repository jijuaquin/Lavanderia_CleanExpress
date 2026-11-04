package com.cleanexpress.clean_express.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "id_cliente", nullable = false)
    private Long idCliente;

    @Column(name = "fecha_recepcion")
    private LocalDateTime fechaRecepcion = LocalDateTime.now();

    @Column(name = "fecha_entrega_estimada")
    private LocalDate fechaEntregaEstimada;

    @Column(nullable = false)
    private String estado; // Recibido, En Proceso, Listo, Entregado

    @Column(nullable = false)
    private Double total;
}

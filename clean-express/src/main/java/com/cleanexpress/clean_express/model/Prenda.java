package com.cleanexpress.clean_express.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "prenda")
public class Prenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prenda")
    private Long idPrenda;

    @Column(name = "id_pedido", nullable = false)
    private Long idPedido;

    @Column(name = "tipo_prenda", nullable = false)
    private String tipoPrenda; // Pantalón, Camisa, Vestido, etc.

    private String color;

    @Column(name = "descripcion_detalles")
    private String descripcionDetalles;

    @Column(name = "precio_unitario", nullable = false)
    private BigDecimal precioUnitario;

    @Column(name = "estado")
    private String estadoPrenda = "Recibida"; // Estado inicial por defecto

    @Column(name = "observaciones")
    private String observaciones;
}
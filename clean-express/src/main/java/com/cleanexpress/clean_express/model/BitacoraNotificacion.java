package com.cleanexpress.clean_express.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bitacora_notificacion")
public class BitacoraNotificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bitacora")
    private Long idBitacora;

    @Column(name = "id_pedido", nullable = false)
    private Long idPedido;

    @Column(nullable = false)
    private String mensaje;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio = LocalDateTime.now();

    @Column(name = "estado_envio", nullable = false)
    private String estadoEnvio; // Envíado, Pendiente, Fallido
}
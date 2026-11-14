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
    @Column(name = "id_notificacion")
    private Long idNotificacion;

    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "telefono")
    private String telefono; // <--- Agregamos este campo

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "estado_envio")
    private String estadoEnvio;
}
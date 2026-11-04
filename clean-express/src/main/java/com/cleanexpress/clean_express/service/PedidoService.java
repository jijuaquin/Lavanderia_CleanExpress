package com.cleanexpress.clean_express.service;

import com.cleanexpress.clean_express.model.BitacoraNotificacion;
import com.cleanexpress.clean_express.model.Pago;
import com.cleanexpress.clean_express.model.Pedido;
import com.cleanexpress.clean_express.model.Prenda;
import com.cleanexpress.clean_express.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class PedidoService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PrendaRepository prendaRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private BitacoraNotificacionRepository bitacoraRepository;

    // Método de prueba para registrar un flujo completo de lavandería
    public void registrarPedidoPrueba(Long idCliente) {
        // 1. Crear el Pedido
        Pedido pedido = new Pedido();
        pedido.setIdCliente(idCliente);
        pedido.setEstado("Recibido");
        pedido.setFechaEntregaEstimada(LocalDate.now().plusDays(2));
        pedido.setTotal(150.00);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // 2. Asociar una Prenda al Pedido
        Prenda prenda = new Prenda();
        prenda.setIdPedido(pedidoGuardado.getIdPedido());
        prenda.setTipoPrenda("Pantalón de mezclilla");
        prenda.setColor("Azul");
        prenda.setDescripcionDetalles("Mancha ligera en la rodilla");
        prenda.setPrecioUnitario(new BigDecimal("150.00"));
        prendaRepository.save(prenda);

        // 3. Registrar el Pago
        Pago pago = new Pago();
        pago.setIdPedido(pedidoGuardado.getIdPedido());
        pago.setMontoPagado(new BigDecimal("150.00"));
        pago.setMetodoPago("Efectivo");
        pagoRepository.save(pago);

        // 4. Crear un registro en la Bitácora de Notificación
        BitacoraNotificacion notificacion = new BitacoraNotificacion();
        notificacion.setIdPedido(pedidoGuardado.getIdPedido());
        notificacion.setMensaje("Su pedido ha sido recibido con éxito en CleanExpress.");
        notificacion.setEstadoEnvio("Enviado");
        bitacoraRepository.save(notificacion);

        System.out.println("¡PEDIDO COMPLETO REGISTRADO CORRECTAMENTE CON ID: " + pedidoGuardado.getIdPedido() + "!");
    }
}
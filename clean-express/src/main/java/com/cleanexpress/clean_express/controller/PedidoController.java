package com.cleanexpress.clean_express.controller;

import com.cleanexpress.clean_express.model.*;
import com.cleanexpress.clean_express.repository.*;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class PedidoController {

    @Autowired private ClienteRepository clienteRepository;
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private PrendaRepository prendaRepository;
    @Autowired private PagoRepository pagoRepository;
    @Autowired private BitacoraNotificacionRepository bitacoraRepository;

    @GetMapping("/recepcion")
    public String mostrarRecepcion(@RequestParam(value = "buscar", required = false) String buscar, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";

        model.addAttribute("clienteEncontrado", null);
        if (buscar != null && !buscar.isEmpty()) {
            List<Cliente> resultados = clienteRepository.findByNombreContainingIgnoreCaseOrTelefonoContaining(buscar, buscar);
            if (!resultados.isEmpty()) {
                model.addAttribute("clienteEncontrado", resultados.get(0));
            }
        }
        return "recepcion";
    }

    @GetMapping("/pedidos/consulta")
    public String consultarPedidos(
            @RequestParam(value = "estado", required = false) String estado,
            Model model,
            HttpSession session
    ) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";

        List<Pedido> listaPedidos;
        if (estado != null && !estado.isEmpty()) {
            listaPedidos = pedidoRepository.findByEstado(estado);
        } else {
            listaPedidos = pedidoRepository.findAll();
        }

        model.addAttribute("pedidos", listaPedidos);
        model.addAttribute("estadoSeleccionado", estado);
        return "consulta_pedidos";
    }

    @PostMapping("/recepcion/guardar")
    public void guardarYGenerarPdf(
            @RequestParam(value = "idClienteExistente", required = false) Long idClienteExistente,
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "telefono", required = false) String telefono,
            @RequestParam(value = "correo", required = false) String correo,
            @RequestParam(value = "direccion", required = false) String direccion,
            @RequestParam("tipoServicio") String tipoServicio,
            @RequestParam("fechaEntrega") LocalDate fechaEntrega,
            @RequestParam("tipoPrenda") List<String> tiposPrenda,
            @RequestParam("color") List<String> colores,
            @RequestParam("descripcion") List<String> descripciones,
            @RequestParam("precioUnitario") List<BigDecimal> preciosUnitarios,
            @RequestParam("montoPago") BigDecimal montoPago,
            @RequestParam("metodoPago") String metodoPago,
            HttpServletResponse response
    ) throws IOException, DocumentException {

        // 1. Gestionar Cliente (Evitar duplicados)
        Cliente cliente;
        if (idClienteExistente != null) {
            cliente = clienteRepository.findById(idClienteExistente).orElseThrow();
        } else {
            cliente = new Cliente();
            cliente.setNombre(nombre);
            cliente.setTelefono(telefono);
            cliente.setCorreo(correo);
            cliente.setDireccion(direccion);
            cliente = clienteRepository.save(cliente);
        }

        // Calcular costo total sumando cada prenda individual
        BigDecimal costoTotal = BigDecimal.ZERO;
        for (BigDecimal precio : preciosUnitarios) {
            if (precio != null) {
                costoTotal = costoTotal.add(precio);
            }
        }

        // 2. Registrar Pedido
        Pedido pedido = new Pedido();
        pedido.setIdCliente(cliente.getIdCliente());
        pedido.setEstado("Recibido");
        pedido.setFechaEntregaEstimada(fechaEntrega);
        pedido.setTotal(costoTotal.doubleValue());
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        // 3. Registrar cada Prenda en la BD
        for (int i = 0; i < tiposPrenda.size(); i++) {
            Prenda prenda = new Prenda();
            prenda.setIdPedido(pedidoGuardado.getIdPedido());
            prenda.setTipoPrenda(tiposPrenda.get(i));
            prenda.setColor(colores.get(i));
            prenda.setDescripcionDetalles(descripciones.get(i));
            prenda.setPrecioUnitario(preciosUnitarios.get(i));
            prenda.setEstadoPrenda("Recibida");
            prendaRepository.save(prenda);
        }

        // 4. Registrar Pago
        Pago pago = new Pago();
        pago.setIdPedido(pedidoGuardado.getIdPedido());
        pago.setMontoPagado(montoPago);
        pago.setMetodoPago(metodoPago);
        pagoRepository.save(pago);

        // Generar notificación automática en bitácora (RF-09)
        enviarNotificacionPedidoListo(pedidoGuardado.getIdPedido(), cliente.getTelefono());

        // 5. Generar Comprobante PDF (RF-03) con todas las prendas
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=Comprobante_Pedido_" + pedidoGuardado.getIdPedido() + ".pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLUE);
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.BLACK);
        Font fontBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.BLACK);

        document.add(new Paragraph("🧺 CleanExpress - Comprobante de Recepción", fontTitulo));
        document.add(new Paragraph("Folio de Pedido: #" + pedidoGuardado.getIdPedido(), fontNormal));
        document.add(new Paragraph("Fecha de Recepción: " + LocalDate.now(), fontNormal));
        document.add(new Paragraph("Fecha de Entrega Estimada: " + fechaEntrega, fontNormal));
        document.add(new Paragraph("Servicio: " + tipoServicio, fontNormal));
        document.add(new Paragraph("------------------------------------------------------------------------------------------------"));
        document.add(new Paragraph("Cliente: " + cliente.getNombre() + " | Tel: " + cliente.getTelefono(), fontNormal));
        document.add(new Paragraph("------------------------------------------------------------------------------------------------"));
        document.add(new Paragraph("Detalle de Prendas Registradas:", fontBold));

        for (int i = 0; i < tiposPrenda.size(); i++) {
            document.add(new Paragraph(" • " + tiposPrenda.get(i) + " (" + colores.get(i) + ") - Desc: " + descripciones.get(i) + " | Subtotal: $" + preciosUnitarios.get(i), fontNormal));
        }

        document.add(new Paragraph("------------------------------------------------------------------------------------------------"));
        document.add(new Paragraph("Costo Total: $" + costoTotal, fontBold));
        document.add(new Paragraph("Abono / Pago Realizado: $" + montoPago, fontNormal));
        document.add(new Paragraph("Saldo Pendiente: $" + costoTotal.subtract(montoPago), fontBold));
        document.add(new Paragraph("------------------------------------------------------------------------------------------------"));
        document.add(new Paragraph("¡Gracias por su preferencia! Conserve este comprobante para recoger su ropa.", fontNormal));

        document.close();
    }

    // RF-09: Registrar notificación al cliente
    public void enviarNotificacionPedidoListo(Long idPedido, String telefonoCliente) {
        BitacoraNotificacion notif = new BitacoraNotificacion();
        notif.setIdPedido(idPedido);
        notif.setTelefono(telefonoCliente); // <--- Guardamos el teléfono aquí
        notif.setFechaEnvio(java.time.LocalDateTime.now());
        notif.setMensaje("Estimado cliente, su ropa del pedido #" + idPedido + " está registrada y en proceso.");
        notif.setEstadoEnvio("Enviado");
        bitacoraRepository.save(notif);
    }

    // Mostrar panel de taller para actualizar prendas (RF-04)
    @GetMapping("/taller/panel")
    public String verPanelTaller(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";

        List<Prenda> listaPrendas = prendaRepository.findAll();
        model.addAttribute("prendas", listaPrendas);
        return "taller_panel";
    }

    // Procesar la actualización de estado de la prenda (RF-04)
    @PostMapping("/taller/actualizar-estado")
    public String actualizarEstadoPrenda(
            @RequestParam("idPrenda") Long idPrenda,
            @RequestParam("nuevoEstado") String nuevoEstado,
            HttpSession session
    ) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";

        Prenda prenda = prendaRepository.findById(idPrenda).orElseThrow();
        prenda.setEstadoPrenda(nuevoEstado);
        prendaRepository.save(prenda);

        return "redirect:/taller/panel";
    }

    // Consultar bitácora de mensajes (RF-09)
    @GetMapping("/notificaciones/bitacora")
    public String verBitacora(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";

        model.addAttribute("notificaciones", bitacoraRepository.findAll());
        return "bitacora_vista";
    }
}
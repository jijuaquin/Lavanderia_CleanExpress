package com.cleanexpress.clean_express;

import com.cleanexpress.clean_express.model.Cliente;
import com.cleanexpress.clean_express.repository.ClienteRepository;
import com.cleanexpress.clean_express.service.PedidoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CleanExpressApplication {

	public static void main(String[] args) {
		SpringApplication.run(CleanExpressApplication.class, args);
	}

	@Bean
	CommandLineRunner initTest(ClienteRepository clienteRepository, PedidoService pedidoService) {
		return args -> {
			// 1. Primero asegurémonos de tener un cliente guardado en la BD
			Cliente c = new Cliente();
			c.setNombre("Carlos Slim");
			c.setTelefono("7779876543");
			c.setCorreo("carlos@email.com");
			c.setDireccion("Temilpa Viejo, Morelos");
			Cliente clienteGuardado = clienteRepository.save(c);

			System.out.println("--- CLIENTE BASE GUARDADO CON ID: " + clienteGuardado.getIdCliente() + " ---");

			// 2. Ejecutamos el servicio para registrar todo el flujo del pedido asociado a este cliente
			pedidoService.registrarPedidoPrueba(clienteGuardado.getIdCliente());
		};
	}
}
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

}
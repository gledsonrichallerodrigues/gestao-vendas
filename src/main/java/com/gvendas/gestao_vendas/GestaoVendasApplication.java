package com.gvendas.gestao_vendas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = { "com.gvendas.gestao_vendas.entidades" })
@EnableJpaRepositories(basePackages = { "com.gvendas.gestao_vendas.repositorio" })
@ComponentScan(basePackages = { "com.gvendas.gestao_vendas.servico", "com.gvendas.gestao_vendas.controlador",
		"com.gvendas.gestao_vendas.excecao", "com.gvendas.gestao_vendas.controlador.util" })
@SpringBootApplication
public class GestaoVendasApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoVendasApplication.class, args);
	}

}

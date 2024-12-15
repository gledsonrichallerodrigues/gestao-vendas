package com.gvendas.gestao_vendas.controlador.util;

import java.util.List;
import java.util.Optional;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;

@Configuration
public class SwaggerConfig {

	public static final String TEST_HEADER = "test-header";
	
	@Bean
	public GroupedOpenApi configuracao() {
		return GroupedOpenApi.builder()
				.group("test-api")
				.pathsToMatch("/**")
				.build();
	}
	 
	@Bean
	public OperationCustomizer customGlobalHeaders() {

		return (Operation operation, HandlerMethod handlerMethod) -> {
			Optional<List<Parameter>> isParameterPresent = Optional.ofNullable(operation.getParameters());
			Boolean isTestHeaderPresent = Boolean.FALSE;
			if (isParameterPresent.isPresent()) {
				isTestHeaderPresent = isParameterPresent.get().stream()
						.anyMatch(param -> param.getName().equalsIgnoreCase(TEST_HEADER));
			}
			if (Boolean.FALSE.equals(isTestHeaderPresent)) {
				Parameter remoteUser = new Parameter().in(ParameterIn.HEADER.toString()).schema(new StringSchema())
						.name(TEST_HEADER).description("Test Header").required(true);
				operation.addParametersItem(remoteUser);
			}
			return operation;
		};
	}
	
	@Bean
	public OpenAPI springOpenAPI() {
		return new OpenAPI()
				.info(new Info()
				.title("Gestão de Vendas")
				.description("Sistema de gestão de vendas")
				.version("1.0.0")
				.license(new License()
						.name("Dev Team")
				.url("https://github.com")))
				.externalDocs(new ExternalDocumentation()
				.description("Test Documentation")
				.url("https://github.com"));
	}
}

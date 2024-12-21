package com.gvendas.gestao_vendas.controlador.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;

@Configuration
public class OpenTelemetryConfig {

	@Bean
	public Tracer tracer() {
		// Verifica se o OpenTelemetry já foi configurado
		if (GlobalOpenTelemetry.getTracer("gestao-vendas") == null) {
			// Exportador Jaeger
			@SuppressWarnings("deprecation")
			JaegerGrpcSpanExporter jaegerExporter = JaegerGrpcSpanExporter.builder()
					.setEndpoint("http://jaeger:4317") // Endpoint do Jaeger
					.build();

			// Configurando o TracerProvider com o JaegerExporter
			SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
					.addSpanProcessor(SimpleSpanProcessor.create(jaegerExporter)).build();

			// Registrando o tracer provider global
			OpenTelemetrySdk.builder().setTracerProvider(tracerProvider).buildAndRegisterGlobal();
		}
		// Retorna o Tracer para ser usado em sua aplicação
		return GlobalOpenTelemetry.getTracer("gestao-vendas");
	}	
}

package com.gvendas.gestao_vendas.controlador;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gvendas.gestao_vendas.entidades.Categoria;
import com.gvendas.gestao_vendas.servico.CategoriaServico;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Categoria", description = "Categoria")
@RestController
@RequestMapping("/categoria")
public class CategoriaControlador {

	@Autowired
	private Tracer tracer;

	private static final Logger logger = LoggerFactory.getLogger(CategoriaControlador.class);

	@Autowired
	private CategoriaServico categoriaServico;

	@WithSpan
	@Operation(summary = "Listar todas")
	@GetMapping
	public List<Categoria> listarTodas() {
		return categoriaServico.listarTodas();
	}

	@WithSpan
	@Operation(summary = "Teste OpenTelemetry")
	@GetMapping("/testeopentelemetry")
	public String testeOpentelemetry() {
		logger.info("Texto Logger Info");
		return "String Teste OpenTelemetry";
	}

	@Operation(summary = "Teste Jaeger")
	@GetMapping("/testejaeger")
	public String testeJaeger() {
        Span span = tracer.spanBuilder("testeJaeger").startSpan();
        try {
        	return "String Teste Jaeger";
        } finally {
            span.end();
        }						
	}

	@WithSpan
	@Operation(summary = "Buscar por id")
	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<Categoria>> buscarPorId(@PathVariable Long codigo) {
		Optional<Categoria> categoria = categoriaServico.buscarPorId(codigo);
		return categoria.isPresent() ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();

	}

	@Operation(summary = "Salvar")
	@PostMapping
	public ResponseEntity<Categoria> salvar(@Valid @RequestBody Categoria categoria) {
		Categoria categoriaSalva = categoriaServico.salvar(categoria);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}

	@Operation(summary = "Atualizar")
	@PutMapping("/{codigo}")
	public ResponseEntity<Categoria> atualizar(@PathVariable Long codigo, @Valid @RequestBody Categoria categoria) {
		return ResponseEntity.ok(categoriaServico.atualizar(codigo, categoria));
	}

	@Operation(summary = "Deletar")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long codigo) {
		categoriaServico.deletar(codigo);
	}
}

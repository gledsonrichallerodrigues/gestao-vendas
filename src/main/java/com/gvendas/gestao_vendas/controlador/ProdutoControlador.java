package com.gvendas.gestao_vendas.controlador;

import java.util.List;
import java.util.Optional;

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

import com.gvendas.gestao_vendas.entidades.Produto;
import com.gvendas.gestao_vendas.servico.ProdutoServico;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Produto", description = "Produto")
@RestController
@RequestMapping("/produto")
public class ProdutoControlador {

	@Autowired
	private ProdutoServico produtoServico;

	@WithSpan
	@Operation(summary = "Listar todos por categoria")
	@GetMapping("/categoria/{codigoCategoria}")
	public ResponseEntity<List<Produto>> listarTodos(@PathVariable Long codigoCategoria) {
		List<Produto> produtos = produtoServico.listarTodos(codigoCategoria);
		return produtos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(produtos);
	}

	@WithSpan
	@Operation(summary = "Buscar por id")
	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<Produto>> buscarPorId(@PathVariable Long codigo) {
		Optional<Produto> produto = produtoServico.buscarPorCodigo(codigo);
		return produto.isPresent() ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();

	}

	@WithSpan
	@Operation(summary = "Salvar")
	@PostMapping
	public ResponseEntity<Produto> salvar(@Valid @RequestBody Produto produto) {
		Produto produtoSalvo = produtoServico.salvar(produto);
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
	}

	@WithSpan
	@Operation(summary = "Atualizar")
	@PutMapping("/{codigo}")
	public ResponseEntity<Produto> atualizar(@PathVariable Long codigo, @Valid @RequestBody Produto produto) {
		return ResponseEntity.ok(produtoServico.atualizar(codigo, produto));
	}

	@WithSpan
	@Operation(summary = "Deletar")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long codigo) {
		produtoServico.deletar(codigo);
	}
}

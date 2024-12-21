package com.gvendas.gestao_vendas.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.gvendas.gestao_vendas.entidades.Produto;
import com.gvendas.gestao_vendas.excecao.RegraNegocioException;
import com.gvendas.gestao_vendas.repositorio.ProdutoRepositorio;

@Service
public class ProdutoServico {

	@Autowired
	private ProdutoRepositorio produtoRepositorio;

	public List<Produto> listarTodos() {
		return produtoRepositorio.findAll();
	}

	@GetMapping
	public Optional<Produto> buscarPorId(Long codigo) {
		return produtoRepositorio.findById(codigo);
	}

	public Produto salvar(Produto produto) {
		validarProdutoDuplicado(produto);
		return produtoRepositorio.save(produto);
	}

	public Produto atualizar(Long codigo, Produto produto) {
		Produto produtoSalvar = validarProdutoExiste(codigo);
		validarProdutoDuplicado(produto);
		BeanUtils.copyProperties(produto, produtoSalvar, "codigo");
		return produtoRepositorio.save(produtoSalvar);
	}

	public void deletar(Long codigo) {
		produtoRepositorio.deleteById(codigo);
	}

	private Produto validarProdutoExiste(Long codigo) {
		Optional<Produto> produto = buscarPorId(codigo);
		if (produto.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return produto.get();
	}

	private void validarProdutoDuplicado(Produto produto) {
		Produto produtoEncontrado = produtoRepositorio.findByDescricao(produto.getDescricao());
		if (produtoEncontrado != null && produtoEncontrado.getCodigo() != produto.getCodigo()) {
			throw new RegraNegocioException(
					String.format("O produto %s já está cadastrado", produto.getDescricao().toUpperCase()));
		}
	}
}
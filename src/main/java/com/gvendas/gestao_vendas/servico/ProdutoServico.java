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
	
	@Autowired
	private CategoriaServico categoriaServico;

	public List<Produto> listarTodos(Long codigoCategoria) {
		return produtoRepositorio.findByCategoriaCodigo(codigoCategoria);
	}

	@GetMapping
	public Optional<Produto> buscarPorCodigo(Long codigo) {
		return produtoRepositorio.buscarPorCodigo(codigo);
	}

	public Produto salvar(Produto produto) {
		validarCategoriaDoProdutoExiste(produto.getCategoria().getCodigo());
		//validarProdutoExiste(produto.getCodigo());
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
		Optional<Produto> produto = buscarPorCodigo(codigo);
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
	
	private void validarCategoriaDoProdutoExiste(Long codigoCategoria) {
		if (codigoCategoria == null) {
			throw new RegraNegocioException("A categoria não pode ser nula");
		}
		
		if (categoriaServico.buscarPorId(codigoCategoria).isEmpty()) {
			throw new RegraNegocioException(String.format("A categoria de código %s informada não existe no cadastro", codigoCategoria)); 
		}
	}
}
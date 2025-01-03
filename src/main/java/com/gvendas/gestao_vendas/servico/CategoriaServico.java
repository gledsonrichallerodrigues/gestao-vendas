package com.gvendas.gestao_vendas.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.gvendas.gestao_vendas.entidades.Categoria;
import com.gvendas.gestao_vendas.excecao.RegraNegocioException;
import com.gvendas.gestao_vendas.repositorio.CategoriaRepositorio;

@Service
public class CategoriaServico {

	@Autowired
	private CategoriaRepositorio categoriaRepositorio;

	public List<Categoria> listarTodas() {
		return categoriaRepositorio.findAll();
	}

	@GetMapping
	public Optional<Categoria> buscarPorId(Long codigo) {
		return categoriaRepositorio.findById(codigo);
	}

	public Categoria salvar(Categoria categoria) {
		validarCategoriaDuplicada(categoria);
		return categoriaRepositorio.save(categoria);
	}

	public Categoria atualizar(Long codigo, Categoria categoria) {
		Categoria categoriaSalvar = validarCategoriaExiste(codigo);
		validarCategoriaDuplicada(categoria);
		BeanUtils.copyProperties(categoria, categoriaSalvar, "codigo");
		return categoriaRepositorio.save(categoriaSalvar);
	}

	public void deletar(Long codigo) {
		categoriaRepositorio.deleteById(codigo);
	}

	private Categoria validarCategoriaExiste(Long codigo) {
		Optional<Categoria> categoria = buscarPorId(codigo);
		if (categoria.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return categoria.get();
	}

	private void validarCategoriaDuplicada(Categoria categoria) {
		Categoria categoriaEncontrada = categoriaRepositorio.findByNome(categoria.getNome());
		if (categoriaEncontrada != null && categoriaEncontrada.getCodigo() != categoria.getCodigo()) {
			throw new RegraNegocioException(
					String.format("A categoria %s já está cadastrada", categoria.getNome().toUpperCase()));
		}
	}
}
package com.gvendas.gestao_vendas.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.gvendas.gestao_vendas.entidades.Categoria;
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
		return categoriaRepositorio.save(categoria);
	}

	public Categoria atualizar(Long codigo, Categoria categoria) {
		Categoria categoriaSalvar = validarCategoriaExiste(codigo);
		BeanUtils.copyProperties(categoria, categoriaSalvar, "codigo");
		return categoriaRepositorio.save(categoriaSalvar);
	}

	private Categoria validarCategoriaExiste(Long codigo) {
		Optional<Categoria> categoria = buscarPorId(codigo);
		if (categoria.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		return categoria.get();
	}
}

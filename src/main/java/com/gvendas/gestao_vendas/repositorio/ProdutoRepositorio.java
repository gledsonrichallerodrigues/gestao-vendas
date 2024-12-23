package com.gvendas.gestao_vendas.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gvendas.gestao_vendas.entidades.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {

	List<Produto> findByCategoriaCodigo (Long codigoCategoria);
	
	@Query("Select prod"
			+ " from Produto prod"
			+ " where prod.codigo = :codigo")
	Optional<Produto> buscarPorCodigo (Long codigo);
	
	Produto findByDescricao(String descricao);
	
	Optional<Produto> findByCategoriaCodigoAndDescricao(Long codigoCategoria, String descricao);
	
}
	

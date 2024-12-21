package com.gvendas.gestao_vendas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gvendas.gestao_vendas.entidades.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {

	Produto findByDescricao(String descricao);
	
}
	

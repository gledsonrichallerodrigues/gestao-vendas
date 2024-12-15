package com.gvendas.gestao_vendas.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gvendas.gestao_vendas.entidades.Categoria;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

}
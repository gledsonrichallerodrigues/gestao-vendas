package com.gvendas.gestao_vendas.entidades;

import java.math.BigDecimal;
import java.util.Objects;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "produto")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo")
	private Long codigo;

	@Column(name = "descricao")
	@NotBlank(message = "Descrição")
	@Length(min = 3, max = 100, message = "Descrição")
	private String descricao;

	@NotNull(message = "Quantidade")
	@Column(name = "quantidade")
	private Integer quantidade;

	@NotNull(message = "Preço custo")
	@Column(name = "preco_custo")
	private BigDecimal precoCusto;

	@NotNull(message = "Preço venda")
	@Column(name = "preco_venda")
	private BigDecimal precoVenda;

	@Length(max = 500, message = "Observação")
	@Column(name = "observacao")
	private String observacao;

	@NotNull(message = "Código da categoria")
	@ManyToOne
	@JoinColumn(name = "codigo_categoria", referencedColumnName = "codigo")
	private Categoria categoria;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(BigDecimal precoCusto) {
		this.precoCusto = precoCusto;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoria, codigo, descricao, observacao, precoCusto, precoVenda, quantidade);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Produto)) {
			return false;
		}
		Produto other = (Produto) obj;
		return Objects.equals(categoria, other.categoria) && Objects.equals(codigo, other.codigo)
				&& Objects.equals(descricao, other.descricao) && Objects.equals(observacao, other.observacao)
				&& Objects.equals(precoCusto, other.precoCusto) && Objects.equals(precoVenda, other.precoVenda)
				&& Objects.equals(quantidade, other.quantidade);
	}

}

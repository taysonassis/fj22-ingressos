package br.com.caelum.ingresso.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.caelum.ingresso.model.desconto.Desconto;

@Entity
public class Ingresso {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Sessao sessao;
	private BigDecimal preco = BigDecimal.ZERO;

	/**
	 * @deprecated	Somente JPA
 	 */
	public Ingresso() {	}
	
	public Ingresso(Sessao sessao, Desconto desconto){
		this.sessao = sessao;
		this.preco = desconto.calculaPrecoFinal(sessao);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Sessao getSessao() {
		return sessao;
	}
	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}
	public BigDecimal getPreco() {
		return preco.setScale(2,RoundingMode.HALF_UP);
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
}

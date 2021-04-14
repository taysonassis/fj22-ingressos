package br.com.caelum.ingresso.model.desconto;

import java.math.BigDecimal;

import br.com.caelum.ingresso.model.Sessao;

public interface Desconto {

	BigDecimal calculaPrecoFinal(Sessao sessao);
	String getDescricao();
}

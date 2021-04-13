package br.com.caelum.ingresso.model.desconto;

import java.math.BigDecimal;

import br.com.caelum.ingresso.model.Sessao;

public class SemDesconto implements Desconto{

	@Override
	public BigDecimal calculaPrecoFinal(Sessao sessao) {
		return sessao.getPreco();
	}
}

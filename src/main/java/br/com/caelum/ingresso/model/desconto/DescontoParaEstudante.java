package br.com.caelum.ingresso.model.desconto;

import java.math.BigDecimal;

import br.com.caelum.ingresso.model.Sessao;

public class DescontoParaEstudante implements Desconto{

	@Override
	public BigDecimal calculaPrecoFinal(Sessao sessao) {
		return sessao.getPreco().multiply(new BigDecimal("0.5"));
	}

	@Override
	public String getDescricao() {
		return "Desconto Estudante";
	}
}

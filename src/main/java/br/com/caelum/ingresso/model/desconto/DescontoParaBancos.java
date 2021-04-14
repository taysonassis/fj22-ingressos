package br.com.caelum.ingresso.model.desconto;

import java.math.BigDecimal;

import br.com.caelum.ingresso.model.Sessao;

public class DescontoParaBancos implements Desconto{

	@Override
	public BigDecimal calculaPrecoFinal(Sessao sessao) {
		return sessao.getPreco().multiply(new BigDecimal("0.7"));
	}

	@Override
	public String getDescricao() {
		return "Desconto Banco";
	}
}

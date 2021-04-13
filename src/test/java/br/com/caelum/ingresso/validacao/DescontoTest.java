package br.com.caelum.ingresso.validacao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Ingresso;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.desconto.SemDesconto;

public class DescontoTest {

	private Sala sala;
	private Filme filme;
	private Sessao sessao;

	@Before
	public void setup() {
		this.sala = new Sala("Sala 3D", new BigDecimal("20.0"));
		this.filme = new Filme("Rouge One", Duration.ofMinutes(120), "Aventura", new BigDecimal("15"));
		this.sessao = new Sessao(LocalTime.parse("10:00:00"), filme, sala);
	}

	@Test
	public void naoDeveConcederDescontoParaIngressoNormal() {

		Ingresso ingresso = new Ingresso(sessao, new SemDesconto());         
		BigDecimal precoEsperado = new BigDecimal("35.00");         
		Assert.assertEquals(precoEsperado, ingresso.getPreco());
	}

}

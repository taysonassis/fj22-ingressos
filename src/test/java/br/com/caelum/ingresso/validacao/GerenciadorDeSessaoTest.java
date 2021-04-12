package br.com.caelum.ingresso.validacao;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.validacao.GerenciadorDeSessao;

public class GerenciadorDeSessaoTest {

	private  Sala sala; 
	private Filme filme;
    private Sessao sessaoDasDez;
	private Sessao sessaoDasTreze;
	private Sessao sessaoDasDezoito;
	
	@Before
	public void setup() {
		this.sala = new Sala("Sala 3D");
		this.filme = new Filme("Rouge One", Duration.ofMinutes(120),"Aventura");
		
		this.sessaoDasDez = new Sessao(LocalTime.parse("10:00:00"), filme, sala);
		this.sessaoDasTreze = new Sessao(LocalTime.parse("13:00:00"), filme, sala);
		this.sessaoDasDezoito = new Sessao(LocalTime.parse("18:00:00"), filme, sala);
	}
	
	@Test
	public void naoDevePermitirSessoesQuandoElasTerminaremNoDiaSeguinte() {
		
		Sessao sessaoQueTerminaNoDiaSeguinte = new Sessao(LocalTime.of(23, 0), filme, sala);
		// AGIR na funcionalidade
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(Collections.emptyList());
		boolean cabe = gerenciador.cabe(sessaoQueTerminaNoDiaSeguinte);
		// ASSEGURAR os resultados esperados
		Assert.assertFalse(cabe);
	}
	
	@Test
	public void devePermitirSessoesQueTerminaNoMesmoDia() {
		
		Sessao sessao = new Sessao(LocalTime.of(19, 0), filme, sala);
		// AGIR na funcionalidade
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(Collections.emptyList());
		boolean cabe = gerenciador.cabe(sessao);
		// ASSEGURAR os resultados esperados
		Assert.assertTrue(cabe);
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessoesTerminandoDentroDoHorarioDeUmaSessaoJaExistente() {
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		Sessao sessao = new Sessao(sessaoDasDez.getHorario().minusHours(1), filme, sala);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciador.cabe(sessao));
	}

	@Test
	public void garanteQueNaoDevePermitirSessoesIniciandoDentroDoHorarioDeUmaSessaoJaExistente() {
		List<Sessao> sessoesDaSala = Arrays.asList(sessaoDasDez);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoesDaSala);
		Sessao sessao = new Sessao(sessaoDasDez.getHorario().plusHours(1), filme, sala);
		Assert.assertFalse(gerenciador.cabe(sessao));
	}

	@Test
	public void garanteQueDevePermitirUmaInsercaoEntreDoisFilmes() {
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez, sessaoDasDezoito);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertTrue(gerenciador.cabe(sessaoDasTreze));
	}

	@Test
	public void garanteQueDeveNaoPermitirUmaSessaoQueTerminaNoProximoDia() {
		List<Sessao> sessoes = Collections.emptyList();
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Sessao sessaoQueTerminaAmanha = new Sessao(LocalTime.parse("23:00:00"), filme, sala);
		Assert.assertFalse(gerenciador.cabe(sessaoQueTerminaAmanha));
	}
	
	
}

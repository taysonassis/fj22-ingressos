package br.com.caelum.ingresso.model.validacao;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessao {
	
	private List<Sessao> sessoesExistentes;
	
	public GerenciadorDeSessao(List<Sessao> sessoesExistentes) {
		this.sessoesExistentes = sessoesExistentes;
	}

	public boolean cabe(Sessao novaSessao) {
		if (terminaAmanha(novaSessao)) {
			return false;
		}
		
		/*
		for (Sessao sessaoExistente : sessoesExistentes) {
			if (horarioConflita(novaSessao, sessaoExistente)) {
				return false;
			}
		}
		*/
		
		return sessoesExistentes.stream()
			.noneMatch(sessaoExistente -> horarioConflita(novaSessao, sessaoExistente));
	}
	
	private boolean horarioConflita(Sessao novaSessao, Sessao sessaoExistente) {
		LocalDateTime inicioDaSessaoExistente = getInicioDaSessaoNaDataDeHoje(sessaoExistente);
		LocalDateTime terminoDaNovaSessao = getTerminoDaSessaoNaDataDeHoje(novaSessao);
		
		boolean terminaAntes = terminoDaNovaSessao.isBefore(inicioDaSessaoExistente);
		
		LocalDateTime inicioDaNovaSessao = getInicioDaSessaoNaDataDeHoje(novaSessao);
		LocalDateTime terminoDaSessaoExistente = getTerminoDaSessaoNaDataDeHoje(sessaoExistente);
		
		boolean comecaDepois = inicioDaNovaSessao.isAfter(terminoDaSessaoExistente);
		
		return !(terminaAntes || comecaDepois);
	}
	
	private boolean terminaAmanha(Sessao sessao) {
		LocalDateTime horarioDeTermino = getTerminoDaSessaoNaDataDeHoje(sessao);
		LocalDateTime ultimoSegundoDeHoje = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
		
		return horarioDeTermino.isAfter(ultimoSegundoDeHoje);
	}

	private LocalDateTime getTerminoDaSessaoNaDataDeHoje(Sessao sessao) {
		LocalDateTime horarioDeInicio = getInicioDaSessaoNaDataDeHoje(sessao);
		
		Duration duracao = sessao.getFilme().getDuracao();
		LocalDateTime horarioDeTermino = horarioDeInicio.plus(duracao);
		
		return horarioDeTermino;
	}
	
	private LocalDateTime getInicioDaSessaoNaDataDeHoje(Sessao sessao) {
		return sessao.getHorario().atDate(LocalDate.now());
	}
	
}
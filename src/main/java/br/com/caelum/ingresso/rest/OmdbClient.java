package br.com.caelum.ingresso.rest;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.caelum.ingresso.model.Filme;

@Component
public class OmdbClient {

	public <T> Optional<T> buscaDetalhesDoFilme(Filme filme, Class<T> tipoDoRetorno) {
		RestTemplate restTemplate = new RestTemplate();
		
		try {
			String url = "http://omdb-fj22.herokuapp.com/movie?title=" + filme.getNome().replace(" ", "+");
			T detalhes = restTemplate.getForObject(url, tipoDoRetorno);
			
			return Optional.of(detalhes);
		} catch (RestClientException e) {
			return Optional.empty();
		}
	}
}

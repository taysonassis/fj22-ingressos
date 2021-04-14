package br.com.caelum.ingresso.rest;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.caelum.ingresso.model.Filme;

@Component
public class OmdbClient {

	public Optional<DetalhesDoFilme> buscadetalhesDoFilme(Filme filme) {
		RestTemplate restTemplate = new RestTemplate();

		String url = "http://omdb-fj22.herokuapp.com/movie?title=" + filme.getNome().replace(" ", "+");

		try {
			DetalhesDoFilme detalhesDoFilme = restTemplate.getForObject(url, DetalhesDoFilme.class);
			return Optional.ofNullable(detalhesDoFilme);
		} catch (RestClientException e) {
			return Optional.empty();
		}
	}
}

package br.com.caelum.ingresso.rest;

import org.springframework.web.client.RestTemplate;

import br.com.caelum.ingresso.model.Filme;

public class OmdbClient {

	
	public DetalhesDoFilme buscadetalhesDoFilme(Filme filme) {
		RestTemplate restTemplate = new RestTemplate();
		
		String url = "http://omdb-fj22.herokuapp.com/movie?title="+ filme.getNome().replace(" ", "+"); 
		DetalhesDoFilme detalhesDoFilme =  restTemplate.getForObject(url, DetalhesDoFilme.class);
		
		return detalhesDoFilme;
	}
}

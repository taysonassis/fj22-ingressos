package br.com.caelum.ingresso.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.form.SessaoForm;
import br.com.caelum.ingresso.model.validacao.GerenciadorDeSessao;
import br.com.caelum.ingresso.rest.DetalhesDoFilme;
import br.com.caelum.ingresso.rest.ImagemCapa;
import br.com.caelum.ingresso.rest.OmdbClient;


@Transactional
@Controller // Anotação: configurar o código
public class SessaoController {
	
	@Autowired
	private FilmeDao filmeDao;
	
	@Autowired
	private SalaDao salaDao;
	
	@Autowired
	private SessaoDao sessaoDao;
	
	@Autowired
	private OmdbClient omdbClient;

	@GetMapping("/admin/sessao")
	public ModelAndView formularioNovaSessao(@RequestParam("salaId") Integer salaId, SessaoForm form) {
		List<Filme> filmes = filmeDao.findAll();
		Sala sala = salaDao.findOne(salaId);
		
		ModelAndView view = new ModelAndView("sessao/sessao");
		view.addObject("filmes", filmes);
		view.addObject("sala", sala);
		view.addObject("form", form);
		
		return view;
	}
	
	@PostMapping("/admin/sessao")
	public ModelAndView salvaSessao(@Valid SessaoForm sessaoForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return formularioNovaSessao(sessaoForm.getSalaId(), sessaoForm);
		}
				
		Sessao novaSessao = sessaoForm.toSessao(salaDao, filmeDao);
		List<Sessao> sessoesExistentes = sessaoDao.buscaSessoesDaSala(novaSessao.getSala());
		
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoesExistentes);
		if (gerenciador.cabe(novaSessao)) {
			sessaoDao.save(novaSessao);
			return new ModelAndView("redirect:/admin/sala/" + sessaoForm.getSalaId() + "/sessoes");
		}
		
		return formularioNovaSessao(sessaoForm.getSalaId(), sessaoForm);
	}
	
	@GetMapping("/sessao/{id}/ingressos")
	public ModelAndView lugares(@PathVariable Integer id) {
		Sessao sessao = sessaoDao.findOne(id);
		
		Optional<ImagemCapa> possivelCapa = omdbClient.buscaDetalhesDoFilme(sessao.getFilme(), ImagemCapa.class);
		
		ModelAndView view = new ModelAndView("sessao/lugares");
		view.addObject("sessao", sessao);
		view.addObject("imagemCapa", possivelCapa.orElse(new ImagemCapa()));
		
		return view;
	}
	
}
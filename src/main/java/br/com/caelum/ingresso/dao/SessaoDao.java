package br.com.caelum.ingresso.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

@Repository
public class SessaoDao {
	
	@PersistenceContext
	private EntityManager manager;
	
	public void save(Sessao sessao) {
		manager.persist(sessao);
	}

	public List<Sessao> buscaSessoesDaSala(Sala sala){
		String jpql = "select s from Sessao s where s.sala = :sala";
		
		return manager.createQuery(jpql, Sessao.class)
		.setParameter("sala", sala)
		.getResultList();
	}
	
	public List<Sessao> buscaSessoesDoFilme(Filme filme){
		String jpql = "select s from Sessao s where s.filme = :filme";
		
		return manager.createQuery(jpql, Sessao.class)
		.setParameter("filme", filme)
		.getResultList();
	}
	
	public Sessao findOne(Integer id) {

        return manager.find(Sessao.class, id);
    }
}

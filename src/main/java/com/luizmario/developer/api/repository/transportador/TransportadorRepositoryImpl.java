package com.luizmario.developer.api.repository.transportador;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.luizmario.developer.api.model.Endereco_;
import com.luizmario.developer.api.model.Transportador;
import com.luizmario.developer.api.model.Transportador_;
import com.luizmario.developer.api.repository.filtro.TransportadorFiltro;

public class TransportadorRepositoryImpl implements TransportadorRepositoryQuery {
	
	@Autowired
	private EntityManager manager;

	@Override
	public Page<Transportador> filtrar(Pageable page, TransportadorFiltro filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Transportador> criteria = builder.createQuery(Transportador.class);
		Root<Transportador> root = criteria.from(Transportador.class);
		
		Predicate[] predicates = criarRestricoes(filtro, builder, root);
		criteria.where(predicates);
		TypedQuery<Transportador> query = manager.createQuery(criteria);
		adicionarRestricoesPaginacao(query, page);
		
		return new PageImpl<>(query.getResultList(), page, calcularTotal(filtro, page));
	}

	private Long calcularTotal(TransportadorFiltro filtro, Pageable page) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Transportador> root = criteria.from(Transportador.class);
		
		Predicate[] predicates = criarRestricoes(filtro, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));		
		return manager.createQuery(criteria).getSingleResult();
	}

	private Predicate[] criarRestricoes(TransportadorFiltro filtro, CriteriaBuilder builder, Root<Transportador> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (filtro != null) {
			
			if (!StringUtils.isEmpty(filtro.getNome())) {
				predicates.add(builder.like(builder.upper(root.get(Transportador_.nome)), "%" + filtro.getNome().toUpperCase() + "¨%"));
			}

			if (!StringUtils.isEmpty(filtro.getEstado())) {
				predicates.add(builder.equal(builder.upper(root.get(Transportador_.endereco).get(Endereco_.estado)), filtro.getNome().toUpperCase()));
			}

			if (!StringUtils.isEmpty(filtro.getCidade())) {
				predicates.add(builder.like(builder.upper(root.get(Transportador_.endereco).get(Endereco_.estado)), "%" + filtro.getCidade().toUpperCase() + "%"));
			}
			
			if (filtro.getTipoModal() != 0) {
				predicates.add(builder.equal(root.get(Transportador_.modalTransporte), filtro.getTipoModal()));
			}
			
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesPaginacao(TypedQuery<Transportador> query, Pageable page) {
		int paginaAtual = page.getPageNumber();
		int totalRegistroPorPagina = page.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistroPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistroPorPagina);
	}
}
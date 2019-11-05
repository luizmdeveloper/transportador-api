package com.luizmario.developer.api.repository.transportador;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.luizmario.developer.api.model.Transportador;
import com.luizmario.developer.api.repository.filtro.TransportadorFiltro;

public interface TransportadorRepositoryQuery {
	
	public Page<Transportador> filtrar(Pageable page, TransportadorFiltro filtro);

}

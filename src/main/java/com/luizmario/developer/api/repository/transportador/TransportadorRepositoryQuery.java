package com.luizmario.developer.api.repository.transportador;

import org.springframework.data.domain.Page;

import com.luizmario.developer.api.model.Transportador;
import com.luizmario.developer.api.repository.filtro.transportador.TransportadorFiltro;

public interface TransportadorRepositoryQuery {
	
	public Page<Transportador> filtrar(Page page, TransportadorFiltro filtro);

}

package com.luizmario.developer.api.repository.transportador;

import java.util.List;

import com.luizmario.developer.api.model.Transportador;
import com.luizmario.developer.api.repository.filtro.TransportadorFiltro;

public interface TransportadorRepositoryQuery {
	
	public List<Transportador> filtrar(TransportadorFiltro filtro);

}

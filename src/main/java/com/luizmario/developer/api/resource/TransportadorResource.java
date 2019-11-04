package com.luizmario.developer.api.resource;

import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luizmario.developer.api.model.Transportador;
import com.luizmario.developer.api.repository.TransportadorRepository;
import com.luizmario.developer.api.repository.filtro.transportador.TransportadorFiltro;
import com.luizmario.developer.api.service.TransportadorService;

@RestController
@RequestMapping("/transportadores")
public class TransportadorResource {
	
	@Autowired
	private TransportadorService transportadorService;
	
	@Autowired
	private TransportadorRepository transportadorRepository;
	
	@GetMapping
	public ResponseEntity<Page<Transportador>> buscar(Page page, TransportadorFiltro filtro) {
		return ResponseEntity.ok(transportadorRepository.filtrar(page, filtro));
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Transportador> buscar(@PathParam(value = "codigo") Long codigo) {
		Optional<Transportador> optionalTransportador = transportadorRepository.findById(codigo);
		return optionalTransportador.isPresent() ? ResponseEntity.ok(optionalTransportador.get()) : ResponseEntity.notFound().build();
	}

}

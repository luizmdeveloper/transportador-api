package com.luizmario.developer.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luizmario.developer.api.model.ModalTransporte;
import com.luizmario.developer.api.repository.ModalTransporteRepository;
import com.luizmario.developer.api.service.exception.ModalTransporteNaoEncontradoException;

@Service
public class ModalTransporteService {
	
	@Autowired
	private ModalTransporteRepository modalTransporteRepository;
	
	
	public ModalTransporte buscarModalTransportePorCodigo(Long codigo) {
		Optional<ModalTransporte> optionalModalTransporte = modalTransporteRepository.findById(codigo);
		
		if (!optionalModalTransporte.isPresent()) {
			throw new ModalTransporteNaoEncontradoException();
		}
		
		return optionalModalTransporte.get();
	}

}

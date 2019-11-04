package com.luizmario.developer.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luizmario.developer.api.model.Transportador;
import com.luizmario.developer.api.repository.TransportadorRepository;

@Service
public class TransportadorService {

	@Autowired
	private TransportadorRepository transportadorRepository;
	
	public Transportador salvar(Transportador transportador){
		return transportadorRepository.save(transportador);
	}
	
}

package com.luizmario.developer.api.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.luizmario.developer.api.model.Transportador;
import com.luizmario.developer.api.repository.TransportadorRepository;
import com.luizmario.developer.api.service.exception.NenhumTelefoneInformadoException;
import com.luizmario.developer.api.service.exception.TransportadorNaoEncontradoException;

@Service
public class TransportadorService {

	@Autowired
	private TransportadorRepository transportadorRepository;
	
	@Autowired
	private ModalTransporteService modalTransporteService;
	
	public Transportador salvar(Transportador transportador) {
		
		if (transportador.getContato() != null) {			
			if (temTelefonePreenchido(transportador)) {
				throw new NenhumTelefoneInformadoException();
			}
		}
		
		modalTransporteService.buscarModalTransportePorCodigo(transportador.getModalTransporte().getCodigo());		
		return transportadorRepository.save(transportador);
	}

	public Transportador alterar(Long codigo, Transportador transportador) {
		Transportador transportadorSalvo = buscarTransportadorPorCodigo(transportador.getCodigo());

		if (transportador.getContato() != null) {			
			if (temTelefonePreenchido(transportador)) {
				throw new NenhumTelefoneInformadoException();
			}
		}
		
		modalTransporteService.buscarModalTransportePorCodigo(transportador.getModalTransporte().getCodigo());
		BeanUtils.copyProperties(transportador, transportadorSalvo, "codigo");
		return transportadorRepository.save(transportadorSalvo);
	}

	public void excluir(Long codigo) {
		Transportador transportador = buscarTransportadorPorCodigo(codigo);
		transportadorRepository.delete(transportador);
	}

	private boolean temTelefonePreenchido(Transportador transportador) {
		return (StringUtils.isEmpty(transportador.getContato().getCelular())) && 
			   (StringUtils.isEmpty(transportador.getContato().getTelefone())) && 
		 	   (StringUtils.isEmpty(transportador.getContato().getWhatsapp()));
	}
	
	private Transportador buscarTransportadorPorCodigo(Long codigo) {
		Optional<Transportador> optionalTransportador = transportadorRepository.findById(codigo);
		
		if (!optionalTransportador.isPresent()) {
			throw new TransportadorNaoEncontradoException();
		}
		
		return optionalTransportador.get();
	}	
}

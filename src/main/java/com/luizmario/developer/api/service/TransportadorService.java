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
import com.luizmario.developer.api.storage.StorageAmazonS3;

@Service
public class TransportadorService {

	@Autowired
	private TransportadorRepository transportadorRepository;
	
	@Autowired
	private ModalTransporteService modalTransporteService;
	
	@Autowired
	private StorageAmazonS3 storage;
	
	public Transportador salvar(Transportador transportador) {
		
		if (transportador.getContato() != null) {			
			if (temTelefonePreenchido(transportador)) {
				throw new NenhumTelefoneInformadoException();
			}
		}
		
		if (StringUtils.hasText(transportador.getFoto())) {
			storage.salvar(transportador.getFoto());
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
		
		if ((StringUtils.isEmpty(transportador.getFoto())) && (StringUtils.hasText(transportadorSalvo.getFoto()))) {
			storage.remover(transportadorSalvo.getFoto());
		} else if ((StringUtils.hasText(transportador.getFoto())) && (!transportador.getFoto().equals(transportadorSalvo.getFoto()))) {
			storage.subistiuir(transportadorSalvo.getFoto(), transportador.getFoto());
		}
		
		modalTransporteService.buscarModalTransportePorCodigo(transportador.getModalTransporte().getCodigo());
		BeanUtils.copyProperties(transportador, transportadorSalvo, "codigo");
		return transportadorRepository.save(transportadorSalvo);
	}

	public void excluir(Long codigo) {
		Transportador transportador = buscarTransportadorPorCodigo(codigo);
		if (StringUtils.hasText(transportador.getFoto())) {
			storage.remover(transportador.getFoto());
		}
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

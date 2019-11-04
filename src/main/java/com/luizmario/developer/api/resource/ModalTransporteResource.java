package com.luizmario.developer.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luizmario.developer.api.model.ModalTransporte;
import com.luizmario.developer.api.repository.ModalTransporteRepository;

@RestController
@RequestMapping("modalTransportes")
public class ModalTransporteResource {
	
	@Autowired
	private ModalTransporteRepository modalTransporteRepository;
		
	@GetMapping
	public ResponseEntity<List<ModalTransporte>> pesquisar() {
		return ResponseEntity.ok(modalTransporteRepository.findAll());
	}

}

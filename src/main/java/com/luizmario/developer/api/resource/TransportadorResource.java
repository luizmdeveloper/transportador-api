package com.luizmario.developer.api.resource;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.luizmario.developer.api.event.RecursoCriadoEvent;
import com.luizmario.developer.api.model.Transportador;
import com.luizmario.developer.api.repository.TransportadorRepository;
import com.luizmario.developer.api.repository.filtro.TransportadorFiltro;
import com.luizmario.developer.api.resource.dto.AnexoFotoDTO;
import com.luizmario.developer.api.service.TransportadorService;
import com.luizmario.developer.api.storage.StorageAmazonS3;

@RestController
@RequestMapping("/transportadores")
public class TransportadorResource {
	
	@Autowired
	private TransportadorService transportadorService;
	
	@Autowired
	private TransportadorRepository transportadorRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private StorageAmazonS3 storage;
	
	@GetMapping
	public ResponseEntity<Page<Transportador>> buscar(Pageable page, TransportadorFiltro filtro) {
		return ResponseEntity.ok(transportadorRepository.filtrar(page, filtro));
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Transportador> buscar(@PathVariable Long codigo) {
		Optional<Transportador> optionalTransportador = transportadorRepository.findById(codigo);
		return optionalTransportador.isPresent() ? ResponseEntity.ok(optionalTransportador.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping("/foto")
	public AnexoFotoDTO uploadImagem(@RequestParam MultipartFile arquivo) {
		String nome = storage.salvarArquivoTemporario(arquivo);
		return new AnexoFotoDTO(nome, storage.montarUrl(nome));
	}
	
	@PostMapping
	public ResponseEntity<Transportador> salvar(@RequestBody @Valid Transportador transportador, HttpServletResponse response) {
		Transportador transportadorSalvo = transportadorService.salvar(transportador);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, transportadorSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(transportadorSalvo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Transportador> alterar(@PathVariable Long codigo, @RequestBody @Valid Transportador transportador){
		return ResponseEntity.ok(transportadorService.alterar(codigo, transportador));
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long codigo) {
		transportadorService.excluir(codigo);
	}
}

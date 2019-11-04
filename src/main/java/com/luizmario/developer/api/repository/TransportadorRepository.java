package com.luizmario.developer.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizmario.developer.api.model.Transportador;
import com.luizmario.developer.api.repository.transportador.TransportadorRepositoryQuery;

public interface TransportadorRepository extends JpaRepository<Transportador, Long>, TransportadorRepositoryQuery {

}

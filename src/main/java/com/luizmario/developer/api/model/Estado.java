package com.luizmario.developer.api.model;

import java.io.Serializable;

public class Estado implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String sigla;
	private String descricao;
	
	public Estado() {}
	
	public Estado(String sigla, String descricao) {
		super();
		this.sigla = sigla;
		this.descricao = descricao;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}

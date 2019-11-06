package com.luizmario.developer.api.resource.dto;

public class AnexoFotoDTO {
	
	private String nome;
	private String url;
	
	public AnexoFotoDTO(String nome, String url) {
		super();
		this.nome = nome;
		this.url = url;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

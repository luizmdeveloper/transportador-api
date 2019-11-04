package com.luizmario.developer.api.repository.filtro.transportador;

public class TransportadorFiltro {
	
	private String estado;
	private String nome;
	private String cidade;
	private int tipoModal;
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCidade() {
		return cidade;
	}
	
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	public int getTipoModal() {
		return tipoModal;
	}
	
	public void setTipoModal(int tipoModal) {
		this.tipoModal = tipoModal;
	}
}

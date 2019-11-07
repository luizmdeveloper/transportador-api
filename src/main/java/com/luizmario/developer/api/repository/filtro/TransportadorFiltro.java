package com.luizmario.developer.api.repository.filtro;

public class TransportadorFiltro {
	
	private String estado;
	private String nome;
	private String cidade;
	private int modal;
	
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
	
	public int getModal() {
		return modal;
	}
	
	public void setModal(int modal) {
		this.modal = modal;
	}
}

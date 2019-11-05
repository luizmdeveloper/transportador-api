package com.luizmario.developer.api.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Contato implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String telefone;
	private String celular;
	private String whatsapp;
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getCelular() {
		return celular;
	}
	
	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	public String getWhatsapp() {
		return whatsapp;
	}
	
	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
	}
}

package com.luizmario.developer.api.repository.listner;

import javax.persistence.PostLoad;

import org.springframework.util.StringUtils;

import com.luizmario.developer.api.TransporteApiApplication;
import com.luizmario.developer.api.model.Transportador;
import com.luizmario.developer.api.storage.StorageAmazonS3;

public class TransportadorListner {
	
	@PostLoad
	public void postLoad(Transportador transportador) {
		if (StringUtils.hasText(transportador.getFoto())) {
			StorageAmazonS3 storage = TransporteApiApplication.getBean(StorageAmazonS3.class);
			transportador.setUrlFoto(storage.montarUrl(transportador.getFoto()));
		}
	}

}

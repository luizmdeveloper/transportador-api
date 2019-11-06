package com.luizmario.developer.api.storage;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Tag;
import com.luizmario.developer.api.config.property.TransportadoraApiProperty;

@Component
public class StorageAmazonS3 {
	
	@Autowired
	private TransportadoraApiProperty property;
	
	@Autowired
	private AmazonS3 amazonS3;
	
	public String salvarArquivoTemporario(MultipartFile arquivo) {
		AccessControlList accessControl = new AccessControlList();
		accessControl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
		
		ObjectMetadata metaData = new ObjectMetadata();
		metaData.setContentType(arquivo.getContentType());
		metaData.setContentLength(arquivo.getSize());
		
		String nomeArquivo = gerarNovoNomeArquivo(arquivo.getOriginalFilename());
		
		try {
			PutObjectRequest objectRequest = new PutObjectRequest(property.getS3().getBucket(), nomeArquivo, arquivo.getInputStream(), metaData);
			objectRequest.setTagging(new ObjectTagging(Arrays.asList(new Tag("expirar", "true"))));
			
			
			amazonS3.putObject(objectRequest);
			
			return nomeArquivo;
		} catch (IOException e) {
			throw new RuntimeException("Erro ao processar requisição de upload de image", e);
		}
	}

	private String gerarNovoNomeArquivo(String nome) {
		return UUID.randomUUID().toString() + "_" + nome;
	} 
}

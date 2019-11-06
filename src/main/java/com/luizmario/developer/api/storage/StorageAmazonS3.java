package com.luizmario.developer.api.storage;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.SetObjectTaggingRequest;
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
	
	public void salvar(String arquivo) {
		SetObjectTaggingRequest setObjectTaggingRequest = new SetObjectTaggingRequest(property.getS3().getBucket(), arquivo, new ObjectTagging(Collections.emptyList()));
		
		amazonS3.setObjectTagging(setObjectTaggingRequest);
	}
	
	public String montarUrl(String objeto) {
		return "\\\\" + property.getS3().getBucket() +
				".s3.amazonaws.com/" + objeto;
	}

	public void remover(String foto) {
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(property.getS3().getBucket(), foto);
		amazonS3.deleteObject(deleteObjectRequest);
	}
	
	public void subistiuir(String fotoAntiga, String fotoNova) {
		if (StringUtils.hasText(fotoAntiga)) {
			this.remover(fotoAntiga);
		}
		
		salvar(fotoNova);		
	}
	
	private String gerarNovoNomeArquivo(String nome) {
		return UUID.randomUUID().toString() + "_" + nome;
	}

}

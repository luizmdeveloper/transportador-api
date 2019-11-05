package com.luizmario.developer.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Transportador.class)
public abstract class Transportador_ {

	public static volatile SingularAttribute<Transportador, Long> codigo;
	public static volatile SingularAttribute<Transportador, Telefone> telefone;
	public static volatile SingularAttribute<Transportador, Endereco> endereco;
	public static volatile SingularAttribute<Transportador, ModalTransporte> modalTransporte;
	public static volatile SingularAttribute<Transportador, String> nome;
	public static volatile SingularAttribute<Transportador, String> email;

}


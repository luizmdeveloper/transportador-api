CREATE TABLE IF NOT EXISTS modal_transportes (
  codigo BIGINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(60) NOT NULL,
  PRIMARY KEY (`codigo`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS transportadores (
  codigo BIGINT NOT NULL AUTO_INCREMENT,
  codigo_modal BIGINT NOT NULL,
  nome VARCHAR(60) NOT NULL,
  email VARCHAR(80) NOT NULL,
  telefone VARCHAR(11) NULL,
  celular VARCHAR(11) NULL,
  whatsapp VARCHAR(11) NULL,
  cep INT NOT NULL,
  estado VARCHAR(2) NOT NULL,
  cidade VARCHAR(60) NOT NULL,
  bairro VARCHAR(45) NULL,
  endereco VARCHAR(60) NOT NULL,
  numero VARCHAR(5) NOT NULL,
  foto BLOB NULL,
  PRIMARY KEY (codigo),
  INDEX modal_transporte_idx_transportes (codigo_modal ASC) VISIBLE,
  CONSTRAINT modal_transportes_fk_transportes
    FOREIGN KEY (codigo_modal)
    REFERENCES modal_transportes (codigo)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
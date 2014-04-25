DROP DATABASE IF EXISTS sisres_db;
CREATE DATABASE sisres_db;
CREATE USER 'testuser'@'localhost' IDENTIFIED BY 'password';
USE sisres_db;
GRANT ALL ON sisres_db.* TO 'testuser'@'localhost';

CREATE TABLE aluno (
 id_aluno INT NOT NULL AUTO_INCREMENT,
 nome VARCHAR(100) NOT NULL,
 cpf VARCHAR(14) NOT NULL,
 telefone VARCHAR(15),
 email VARCHAR(60),
 matricula VARCHAR(15) NOT NULL,
 PRIMARY KEY (id_aluno)
);


CREATE TABLE equipamento (
 id_equipamento INT NOT NULL AUTO_INCREMENT,
 codigo VARCHAR(15) NOT NULL,
 descricao VARCHAR(120),
 PRIMARY KEY (id_equipamento)
);


CREATE TABLE professor (
 id_professor INT NOT NULL AUTO_INCREMENT,
 nome VARCHAR(100) NOT NULL,
 cpf VARCHAR(14) NOT NULL,
 telefone VARCHAR(15),
 email VARCHAR(60),
 matricula VARCHAR(15) NOT NULL,
 PRIMARY KEY (id_professor)
);


CREATE TABLE reserva_equipamento_professor (
 id_reserva_equipamento_professor INT NOT NULL AUTO_INCREMENT,
 id_professor INT NOT NULL,
 id_equipamento INT NOT NULL,
 hora VARCHAR(5) NOT NULL,
 data VARCHAR(10) NOT NULL,
 PRIMARY KEY (id_reserva_equipamento_professor)
);


CREATE TABLE sala (
 id_sala INT NOT NULL AUTO_INCREMENT,
 codigo VARCHAR(10) NOT NULL,
 descricao VARCHAR(120),
 capacidade INT,
 PRIMARY KEY (id_sala)
);


CREATE TABLE reserva_sala_aluno (
 id_reserva_sala_aluno INT NOT NULL AUTO_INCREMENT,
 id_aluno INT NOT NULL,
 id_sala INT NOT NULL,
 finalidade VARCHAR(150) NOT NULL,
 hora VARCHAR(5) NOT NULL,
 data VARCHAR(10) NOT NULL,
 cadeiras_reservadas INT NOT NULL,
 PRIMARY KEY (id_reserva_sala_aluno)
);


CREATE TABLE reserva_sala_professor (
 id_reserva_sala_professor INT NOT NULL AUTO_INCREMENT,
 id_professor INT NOT NULL,
 id_sala INT NOT NULL,
 finalidade VARCHAR(150) NOT NULL,
 hora VARCHAR(5) NOT NULL,
 data VARCHAR(10) NOT NULL,
 PRIMARY KEY (id_reserva_sala_professor)
);


ALTER TABLE reserva_equipamento_professor ADD CONSTRAINT FK_reserva_equipamento_professor_0 FOREIGN KEY (id_professor) REFERENCES professor (id_professor);
ALTER TABLE reserva_equipamento_professor ADD CONSTRAINT FK_reserva_equipamento_professor_1 FOREIGN KEY (id_equipamento) REFERENCES equipamento (id_equipamento);


ALTER TABLE reserva_sala_aluno ADD CONSTRAINT FK_reserva_sala_aluno_0 FOREIGN KEY (id_aluno) REFERENCES aluno (id_aluno);
ALTER TABLE reserva_sala_aluno ADD CONSTRAINT FK_reserva_sala_aluno_1 FOREIGN KEY (id_sala) REFERENCES sala (id_sala);


ALTER TABLE reserva_sala_professor ADD CONSTRAINT FK_reserva_sala_professor_0 FOREIGN KEY (id_professor) REFERENCES professor (id_professor);
ALTER TABLE reserva_sala_professor ADD CONSTRAINT FK_reserva_sala_professor_1 FOREIGN KEY (id_sala) REFERENCES sala (id_sala);



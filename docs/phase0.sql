DROP DATABASE phase0;
CREATE DATABASE phase0;
USE phase0;

CREATE TABLE Horarios(
  hoId int not null AUTO_INCREMENT,
  diId int not null,
  hoDay enum('mon', 'tue', 'wed', 'thr', 'fri', 'sat'),
  hoTime enum('14h00', '15h30', '18h30', '20h00'),
  primary key(hoId)
);

CREATE TABLE Alunos(
  alId int not null AUTO_INCREMENT,
  alNumber int not null,
  alName varchar(150) not null,
  unique (alNumber),
  primary key(alId, alNumber)
);

CREATE TABLE Disciplinas(
  diId int not null AUTO_INCREMENT,
  diName varchar(100) not null,
  diDesc varchar(200) not null,
  diSala varchar(20) not null,
  unique (diId),
  primary key(diId)
);

CREATE TABLE Inscricoes(
  inId int not null AUTO_INCREMENT,
  alNumber int not null,
  diId int not null,
  hoId int not null,
  foreign key(alNumber) references Alunos(alNumber),
  foreign key(diId) references Disciplinas(diId),
  foreign key(hoId) references Horarios(hoId),
  primary key(inId)
);


INSERT INTO Horarios (diId, hoDay, hoTime) VALUES (1, 'mon', '18h30');
INSERT INTO Horarios (diId, hoDay, hoTime) VALUES (1, 'thr', '20h00');
INSERT INTO Horarios (diId, hoDay, hoTime) VALUES (2, 'mon', '20h00');
INSERT INTO Horarios (diId, hoDay, hoTime) VALUES (2, 'thr', '18h30');
INSERT INTO Horarios (diId, hoDay, hoTime) VALUES (3, 'tue', '18h30');
INSERT INTO Horarios (diId, hoDay, hoTime) VALUES (3, 'fri', '20h00');

INSERT INTO Alunos (alNumber, alName) VALUES (26958, 'Marcelo Correa');
INSERT INTO Alunos (alNumber, alName) VALUES (20000, 'Ze Manel');
INSERT INTO Alunos (alNumber, alName) VALUES (22000, 'To Ze');
INSERT INTO Alunos (alNumber, alName) VALUES (23000, 'Quim Manel');

INSERT INTO Disciplinas (diId, diName, diDesc, diSala) VALUES (1, 'LS', 'Laboratorio de Software', 'G1.07');
INSERT INTO Disciplinas (diId, diName, diDesc, diSala) VALUES (2, 'PF', 'Programação Funcional', 'G1.03');
INSERT INTO Disciplinas (diId, diName, diDesc, diSala) VALUES (3, 'AC', 'Arquitectura de Computadores', 'G0.02');

INSERT INTO Inscricoes (alNumber, diId, hoId) VALUES (26958, 1, 1);
INSERT INTO Inscricoes (alNumber, diId, hoId) VALUES (26958, 2, 2);
INSERT INTO Inscricoes (alNumber, diId, hoId) VALUES (26958, 3, 3);
INSERT INTO Inscricoes (alNumber, diId, hoId) VALUES (20000, 2, 2);
INSERT INTO Inscricoes (alNumber, diId, hoId) VALUES (23000, 2, 2);


-- horarios das disciplinas inscritas pelo aluno 26958
SELECT alName, alNumber, diName, diDesc, diSala, hoDay, hoTime FROM
  (SELECT  * FROM
    (SELECT alName, Alunos.AlNumber, diId, hoId FROM Alunos INNER JOIN Inscricoes ON (Alunos.alNumber = Inscricoes.alNumber)) AS ALUNOS
  WHERE ALUNOS.alNumber = 26958) AS SINGLE
INNER JOIN
  (SELECT Horarios.diId, diName, diDesc, diSala, hoDay, hoTime FROM Disciplinas INNER JOIN  Horarios ON (Disciplinas.diId = Horarios.diId)) AS DISCP
ON (SINGLE.diId = DISCP.diId)



DROP DATABASE IF EXISTS dbTest;
CREATE DATABASE dbTest;
USE dbTest;

CREATE TABLE templ (
  temId int not null AUTO_INCREMENT,
  temName varchar(80) not null,
  temDesc varchar(200) null,
  primary key(temId)
);

CREATE TABLE chklst (
  chkId int not null AUTO_INCREMENT,
  chkName varchar(80) not null,
  chkDesc varchar(200) not null,
  chkDueDate TIMESTAMP NULL,
  chkIsCompleted BOOLEAN not null DEFAULT 1,
  primary key(chkId)
);

CREATE TABLE task (
  tskId int not null AUTO_INCREMENT,
  tskChkId int null,
  tskTemId int null,
  tskOrder int not null,
  tskName varchar(80) not null,
  tskDesc varchar(200) not null,
  tskDueDate TIMESTAMP NULL,
  tskIsCompleted BOOLEAN not null DEFAULT 0,
  foreign key(tskChkId) references chklst(chkId),
  foreign key(tskTemId) references templ(temId),
  primary key(tskId)
);

INSERT INTO chklst (chkName, chkDesc) VALUES ('clsample1', 'clsample1 desc');
INSERT INTO chklst (chkName, chkDesc, chkDueDate) VALUES ('clsample2', 'clsample2 desc', '2000-01-01 00:01');
INSERT INTO chklst (chkName, chkDesc) VALUES ('clsample3', 'clsample3 desc');
INSERT INTO templ (temName, temDesc) VALUES ('tmsample1', 'tmsample1 desc');
INSERT INTO templ (temName, temDesc) VALUES ('tmsample2', 'tmsample2 desc');

SET @last = IFNULL(0, (SELECT MAX(tskOrder) FROM task WHERE tskChkId = 1)); -- SELECT @last;
INSERT INTO task (tskChkId, tskOrder, tskName, tskDesc) VALUES (1,(@last := @last + 1), 'tsk01.1', 'tsk01.1 desc');
INSERT INTO task (tskChkId, tskOrder, tskName, tskDesc) VALUES (1,(@last := @last + 1), 'tsk02.1', 'tsk02.1 desc');

SET @last = IFNULL(0, (SELECT MAX(tskOrder) FROM task WHERE tskChkId = 1)); -- SELECT @last;
INSERT INTO task (tskChkId, tskOrder, tskName, tskDesc, tskDueDate) VALUES (2,(@last := @last + 1), 'tsk01.2', 'tsk01.2 desc', '2001-01-01 13:00');
INSERT INTO task (tskChkId, tskOrder, tskName, tskDesc, tskDueDate) VALUES (2,(@last := @last + 1), 'tsk02.2', 'tsk02.2 desc', '2001-01-01 10:00');

SET @last = IFNULL(0, (SELECT MAX(tskOrder) FROM task WHERE tskChkId = 1)); -- SELECT @last;
INSERT INTO task (tskTemId, tskOrder, tskName, tskDesc) VALUES (1,(@last := @last + 1), 'tsk01.3', 'tsk01.3 desc');
INSERT INTO task (tskTemId, tskOrder, tskName, tskDesc) VALUES (1,(@last := @last + 1), 'tsk02.3', 'tsk02.3 desc');

DROP DATABASE clmanager;
CREATE DATABASE clmanager;
USE clmanager;


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


-- populating
INSERT INTO chklst (chkName, chkDesc) VALUES ('Almocos', 'Almocos da semana');
INSERT INTO chklst (chkName, chkDesc, chkDueDate) VALUES ('Peq Almoços', 'pequenos Almocos da semana', '2016-10-15 16:00');
INSERT INTO chklst (chkName, chkDesc, chkDueDate) VALUES ('Jantares', 'Jantares da semana', '2016-10-05 13:00');
INSERT INTO task (tskChkId, tskOrder, tskName, tskDesc, tskDueDate) VALUES (4, 0, 'almoco 2a feira', 'comer tiras na Povoa da Galega na 2a. feira', '2016-10-03 13:00');

SET @last = IFNULL(0, (SELECT MAX(tskOrder) FROM task WHERE tskChkId = 1)); -- SELECT @last;
INSERT INTO task (tskChkId, tskOrder, tskName, tskDesc, tskDueDate) VALUES (1,(@last := @last + 1), 'almoco 2a feira', 'comer tiras na Povoa da Galega na 2a. feira', '2016-10-03 13:00');
INSERT INTO task (tskChkId, tskOrder, tskName, tskDesc, tskDueDate) VALUES (1,(@last := @last + 1), 'almoco 3a feira', 'comer lombinho na vara do louro em Almeirim na 3a. feira', '2016-10-04 13:00');
INSERT INTO task (tskChkId, tskOrder, tskName, tskDesc, tskDueDate) VALUES (1,(@last := @last + 1), 'almoco 4a feira', 'comer Secretos no Ze Pinto na 4a. feira', '2016-10-05 13:00');
INSERT INTO task (tskChkId, tskOrder, tskName, tskDesc, tskDueDate) VALUES (1,(@last := @last + 1), 'almoco 5a feira', 'comer uma Posta Mirandesa no Castelo na 5a. feira', '2016-10-06 13:00');
INSERT INTO task (tskChkId, tskOrder, tskName, tskDesc, tskDueDate) VALUES (1,(@last := @last + 1), 'almoco 6a feira', 'comer mais uma Posta Mirandesa no Castelo na 6a. feira', '2016-10-07 13:00');

INSERT INTO templ (temName, temDesc) VALUES ('Refeicoes do Dia', 'Refeicoes do Dia');

SET @last = IFNULL(0, (SELECT MAX(tskOrder) FROM task WHERE tskTemId = 1));
INSERT INTO task (tskTemId, tskOrder, tskName, tskDesc) VALUES (1,(@last := @last + 1), 'PeqAlmoco', 'o que comer ao pequeno almoço');
INSERT INTO task (tskTemId, tskOrder, tskName, tskDesc) VALUES (1,(@last := @last + 1), 'Almoco', 'o que comer ao almoço');
INSERT INTO task (tskTemId, tskOrder, tskName, tskDesc) VALUES (1,(@last := @last + 1), 'Lanche', 'o que comer ao lanche');
INSERT INTO task (tskTemId, tskOrder, tskName, tskDesc) VALUES (1,(@last := @last + 1), 'Seia', 'o que comer a Seia');

-- checklists with tasks
SELECT * FROM chklst JOIN task ON chklst.chkId = task.tskChkId;
-- templates with tasks
SELECT * FROM templ JOIN task ON templ.temId = task.tskTemId;


-- re-ordering checklists inside a checklist
-- http://paulwhippconsulting.com/blog/renumbering-an-ordering-field-in-mysql/
-- update table set col = (select count(*) from (select col from table) as temptable where temptable.col <table.col );
--- SET @ordering_inc = 10;
--- SET @new_ordering = 0;
--- UPDATE tasks SET ordering = (@new_ordering := @new_ordering + @ordering_inc) ORDER BY ordering ASC


-- CMD_GETCheckLstOpenNumTsks
SELECT * FROM
  (SELECT * FROM chklst WHERE chkIsCompleted = FALSE) AS C
JOIN
  (SELECT tskChkId, count(tskChkId) as NUMTSKS FROM task GROUP BY tskChkId ) AS T
    ON C.chkId = tskChkId
ORDER BY NUMTSKS DESC;
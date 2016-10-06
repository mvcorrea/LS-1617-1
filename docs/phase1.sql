DROP DATABASE clmanager;
CREATE DATABASE clmanager;
USE clmanager;

CREATE TABLE chklst (
  chkId int not null AUTO_INCREMENT,
  chkName varchar(80) not null,
  chkDesc varchar(200) not null,
  chkDueDate TIMESTAMP NULL,
  chkIsCompleted BOOLEAN not null default 1,
  primary key(chkId)
);

CREATE TABLE task (
  tskId int not null AUTO_INCREMENT,
  tskChkId int not null,
  tskOrder int not null UNIQUE,
  tskName varchar(80) not null,
  tskDesc varchar(200) not null,
  tskDueDate TIMESTAMP NULL,
  tskIsCompleted BOOLEAN not null default 0,
  foreign key(tskChkId) references chklst(chkId),
  primary key(tskId)
);

CREATE TABLE chklstTmpl (
  chkId int not null AUTO_INCREMENT,
  chkName varchar(80) not null,
  chkDesc varchar(200) not null,
  chkDueDate TIMESTAMP NULL,
  chkIsCompleted BOOLEAN not null default 1,
  primary key(chkId)
);


-- populating
INSERT INTO chklst (chkName, chkDesc) VALUES ('Almocos', 'Almocos da semana');

INSERT INTO task (tskChkId, tskName, tskDesc, tskDueDate) VALUES (1, 'almoco 2a feira', 'comer tiras na Povoa da Galega na 2a. feira', '2016-10-03 13:00');
INSERT INTO task (tskChkId, tskName, tskDesc, tskDueDate) VALUES (1, 'almoco 3a feira', 'comer lombinho na vara do louro em Almeirim na 3a. feira', '2016-10-04 13:00');
INSERT INTO task (tskChkId, tskName, tskDesc, tskDueDate) VALUES (1, 'almoco 4a feira', 'comer Secretos no Ze Pinto na 4a. feira', '2016-10-05 13:00');
INSERT INTO task (tskChkId, tskName, tskDesc, tskDueDate) VALUES (1, 'almoco 5a feira', 'comer uma Posta Mirandesa no Castelo na 5a. feira', '2016-10-06 13:00');
INSERT INTO task (tskChkId, tskName, tskDesc, tskDueDate) VALUES (1, 'almoco 6a feira', 'comer uma Posta Mirandesa no Castelo na 6a. feira', '2016-10-07 13:00');

INSERT INTO task () VALUES ();

-- checklists with tasks
SELECT * FROM chklst JOIN task ON chklst.chkId = task.tskChkId;

-- re-ordering checklists inside a checklist
-- http://paulwhippconsulting.com/blog/renumbering-an-ordering-field-in-mysql/
-- update table set col = (select count(*) from (select col from table) as temptable where temptable.col <table.col );
SET @ordering_inc = 10;
SET @new_ordering = 0;
UPDATE tasks SET ordering = (@new_ordering := @new_ordering + @ordering_inc) ORDER BY ordering ASC




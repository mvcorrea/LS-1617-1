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
  chkTempl int NULL,
  chkName varchar(80) not null,
  chkDesc varchar(200) not null,
  chkDueDate TIMESTAMP NULL,
  chkIsCompleted BOOLEAN not null DEFAULT 1,
  foreign key(chkTempl) references templ(temId),
  primary key(chkId)
);

CREATE TABLE task (
  tskId int not null AUTO_INCREMENT,
  tskChkId int null,
  tskTemId int null,
  tskName varchar(80) not null,
  tskDesc varchar(200) not null,
  tskDueDate TIMESTAMP NULL,
  tskIsCompleted BOOLEAN not null DEFAULT 0,
  foreign key(tskChkId) references chklst(chkId),
  foreign key(tskTemId) references templ(temId),
  primary key(tskId)
);

CREATE TABLE tag (
  tagId int not null AUTO_INCREMENT,
  tagName varchar(80) not null,
  tagColor varchar(80) not null,
  primary key(tagId)
);

CREATE TABLE chk2tag (
  xId int not null AUTO_INCREMENT,
  xtagId int not null,
  xchkId int not null,
  foreign key(xtagId) references tag(tagId),
  foreign key(xchkId) references chklst(chkId),
  primary key(xId, xtagId, xchkId)
);




-- -- chk simple #1 "praias Porto" -------------------------------------------------------------------------------------
INSERT INTO chklst (chkName, chkDesc) VALUES ('praiasPT1', 'praias porto #1');
-- add 2 tasks in CHK #1 (basic)
INSERT INTO task (tskChkId, tskName, tskDesc) VALUES (1, 'boavista', 'praia da boavista');
INSERT INTO task (tskChkId, tskName, tskDesc) VALUES (1, 'aguda', 'praia da aguda');


-- -- chk simple #2 "museus Porto" -------------------------------------------------------------------------------------
INSERT INTO chklst (chkName, chkDesc, chkDueDate) VALUES ('museusPT1', 'museus porto #1', '2015-08-01 12:01');
-- add 2 tasks in CHK #2 (with dueDate)
INSERT INTO task (tskChkId, tskName, tskDesc, tskDueDate) VALUES (2, 'fcp', 'museu do FCP', '2001-01-01 13:00');
INSERT INTO task (tskChkId, tskName, tskDesc, tskDueDate) VALUES (2, 'vinho', 'museu do vinho do porto', '2001-01-01 10:00');


-- -- chk from template ------------------------------------------------------------------------------------------------
INSERT INTO templ (temName, temDesc) VALUES ('museusLX1', 'museus lisboa #1 templ');
-- add 3 tasks in template #1 (Museus)

INSERT INTO task (tskTemId, tskName, tskDesc) VALUES (1, 'maat', 'museu de arte, arquitetura e tecnologia');
INSERT INTO task (tskTemId, tskName, tskDesc) VALUES (1, 'coches', 'museu do coches');
INSERT INTO task (tskTemId, tskName, tskDesc) VALUES (1, 'traje', 'museu do traje');
-- checklist
INSERT INTO chklst (chkName, chkDesc, chkTempl) VALUES ('museusLX1@tmpl1', 'museus checklist #1', 1);
INSERT INTO chklst (chkName, chkDesc, chkTempl) VALUES ('museusLX1@tmpl1', 'museus checklist #2', 1);
-- template implementation CHK #3

INSERT INTO task (tskChkId, tskName, tskDesc, tskDueDate) VALUES (3, 'maat', 'museu de arte, arquitetura e tecnologia', '2016-01-02 12:35');
INSERT INTO task (tskChkId, tskName, tskDesc, tskDueDate) VALUES (3, 'coches', 'museu do coches', '2016-03-02 12:40');
INSERT INTO task (tskChkId, tskName, tskDesc, tskDueDate) VALUES (3, 'traje', 'museu do traje', '2016-02-02 12:20');
-- template implementation CHK #4
INSERT INTO task (tskChkId, tskName, tskDesc) VALUES (4, 'maat', 'museu de arte, arquitetura e tecnologia');
INSERT INTO task (tskChkId, tskName, tskDesc) VALUES (4, 'coches', 'museu do coches');
INSERT INTO task (tskChkId, tskName, tskDesc) VALUES (4, 'traje', 'museu do traje');


-- -- chk from template ------------------------------------------------------------------------------------------------
INSERT INTO templ (temName, temDesc) VALUES ('praiasLX1', 'praias lisboa #2 templ');
-- add 2 tasks in template #2 (Museus)
INSERT INTO task (tskTemId, tskName, tskDesc) VALUES (2, 'guincho', 'praia do guincho');
INSERT INTO task (tskTemId, tskName, tskDesc) VALUES (2, 'portinho', 'portinho da arrabida');
-- template implementation CHK #5
INSERT INTO chklst (chkName, chkDesc, chkIsCompleted, chkTempl) VALUES ('praiasLX1@tmpl2', 'praias checklist #1', FALSE, 2 );
-- template implementation
INSERT INTO task (tskChkId, tskName, tskDesc, tskDueDate) VALUES (5, 'guincho', 'praia do guincho', '2010-01-02 12:35');
INSERT INTO task (tskChkId, tskName, tskDesc, tskDueDate) VALUES (5, 'portinho', 'portinho da arrabida', '2010-02-02 13:45');


-- -- chk from template ------------------------------------------------------------------------------------------------
INSERT INTO templ (temName, temDesc) VALUES ('restaurantes', 'restaurantes LX #3 templ');
-- add 2 tasks in template #1 (restaurantes)

INSERT INTO task (tskTemId, tskName, tskDesc) VALUES (3, 'lagarto', 'restaurante a tasca do lagarto');
INSERT INTO task (tskTemId, tskName, tskDesc) VALUES (3, 'ze pinto', 'restaurante ze pinto');
-- template implementation CHK #6
INSERT INTO chklst (chkName, chkDesc, chkIsCompleted, chkTempl) VALUES ('restaurantesLX1@tmpl3', 'restaurantes checklist #1', FALSE, 3 );
INSERT INTO task (tskChkId, tskName, tskDesc, tskIsCompleted) VALUES (6, 'lagarto', 'restaurante a tasca do lagarto', FALSE);
INSERT INTO task (tskChkId, tskName, tskDesc, tskIsCompleted) VALUES (6, 'ze pinto', 'restaurante ze pinto', FALSE);


-- -- chk simple #7 "restaurantes Porto" -------------------------------------------------------------------------------
INSERT INTO chklst (chkName, chkDesc, chkDueDate) VALUES ('restaurantesPT1', 'restaurantes porto #1', '2015-08-01 12:01');
-- add 2 tasks in checklist #6 (with dueDate) CHK #7
INSERT INTO task (tskChkId, tskName, tskDesc, tskDueDate) VALUES (7, 'capa negra', 'restaurante capa negra', '2006-01-01 13:30');
INSERT INTO task (tskChkId, tskName, tskDesc, tskDueDate) VALUES (7, 'sandeira', 'restaurante a sandeira', '2003-01-01 10:30');


-- -- others -----------------------------------------------------------------------------------------------------------
-- empty template
INSERT INTO templ (temName, temDesc) VALUES ('parques', 'parques LX #4 templ');
-- empty checklist
INSERT INTO chklst (chkName, chkDesc, chkDueDate, chkIsCompleted) VALUES ('museusLX2', 'museus checklist #3', '2017-01-01 13:21', FALSE );
INSERT INTO chklst (chkName, chkDesc, chkDueDate, chkIsCompleted) VALUES ('restaurantesPT1', 'restaurantes porto #1', '2002-01-01 14:51', FALSE );
-- tags
INSERT INTO tag (tagName, tagColor) VALUES ('work', 'blue');
INSERT INTO tag (tagName, tagColor) VALUES ('vacation', 'green');
INSERT INTO chk2tag (xtagId, xchkId) VALUES (1,1);
INSERT INTO chk2tag (xtagId, xchkId) VALUES (2,2);


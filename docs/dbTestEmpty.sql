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



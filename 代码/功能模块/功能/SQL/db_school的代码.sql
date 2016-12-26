D:\Programs\SQL2008\MSSQL10.MSSQLSERVER\MSSQL\DATA

--���
CREATE TABLE tb_identity(
identityId INT identity(1,1) PRIMARY KEY, --�Զ�����
_identity NVARCHAR(10) NOT NULL
);

--ϵ��
CREATE TABLE tb_department(
departmentId INT identity(1,1) PRIMARY KEY, --�Զ�����
_department NVARCHAR(10) NOT NULL
);

--�༶
CREATE TABLE tb_class(
classId INT identity(1,1) PRIMARY KEY, --�Զ�����
_class NVARCHAR(20) NOT NULL,
departmentId int NOT NULL,
FOREIGN KEY(departmentId) references tb_department(departmentId) --���
);

--ѧ����
CREATE TABLE tb_student(
	_id varchar(8) PRIMARY KEY, --����
	name nvarchar(8) NOT NULL,
	age int check(age>0 and age<150) NULL, --���Լ��
	sex char(2) default '��' check (sex in ('��','Ů')), --���Լ��
	exerciseTimes int default 0,	--Ĭ��Լ��
	pwd nvarchar(20) default '123456',
	fk_identityId int default 1,
	fk_classId int NOT NULL,
	FOREIGN KEY(fk_identityId) references tb_identity(identityId), --��� tb_identity��
	FOREIGN KEY(fk_classId) references tb_class(classId) --��� tb_class��
);

--��ʦ/����Ա ��
CREATE TABLE tb_teacher(
	_id varchar(10) PRIMARY KEY, --����
	name nvarchar(8)  NOT NULL,
	age int check(age>0 and age<150) NULL, --���Լ��
	sex char(2) default '��' check (sex in ('��','Ů')), --���Լ��
	pwd nvarchar(20) NOT NULL,
	fk_identityId int default 2, --Ĭ��Լ��
	fk_departmentId int NOT NULL,
	FOREIGN KEY(fk_identityId) references tb_identity(identityId), --��� tb_identity��
	FOREIGN KEY(fk_departmentId) references tb_department(departmentId) --��� tb_department��
);

--���ѧ��
insert into tb_student(_id,name,age,sex,pwd,fk_classId) values ('51340142','�����',21,'��','123456',1)
insert into tb_student(_id,name,age,sex,pwd,fk_classId) values ('51340143','����',21,'��','123456',1)
insert into tb_student(_id,name,age,sex,pwd,fk_classId) values ('51340144','������',20,'��','123456',1)
insert into tb_student(_id,name,age,sex,pwd,fk_classId) values ('51110142','��ݸ��',20,'Ů','123456',5)
insert into tb_student(_id,name,age,sex,pwd,fk_classId) values ('51220142','�ɵ���',21,'��','123456',7)
insert into tb_student(_id,name,age,sex,pwd,fk_classId) values ('51330142','���˾�',20,'��','123456',9)
insert into tb_student(_id,name,age,sex,pwd,fk_classId) values ('51440142','�ִ�',22,'��','123456',11)
--��ӽ�ʦ
insert into tb_teacher(_id,name,age,sex,pwd,fk_departmentId) values ('1100','�ܽ���',36,'��','123456',1)
insert into tb_teacher(_id,name,age,sex,pwd,fk_departmentId) values ('2100','����',29,'Ů','123456',2)
insert into tb_teacher(_id,name,age,sex,pwd,fk_departmentId) values ('3100','�Ž�',32,'��','123456',3)
insert into tb_teacher(_id,name,age,sex,pwd,fk_departmentId) values ('4100','������',34,'Ů','123456',4)
--��ӹ���Ա
insert into tb_teacher(_id,name,age,sex,pwd,fk_identityId,fk_departmentId) values ('001','����',51,'��','123456',3,1)
insert into tb_teacher(_id,name,age,sex,pwd,fk_identityId,fk_departmentId) values ('002','����',44,'��','123456',3,2)
insert into tb_teacher(_id,name,age,sex,pwd,fk_identityId,fk_departmentId) values ('003','�����',47,'��','123456',3,3)
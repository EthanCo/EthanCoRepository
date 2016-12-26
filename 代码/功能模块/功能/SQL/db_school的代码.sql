D:\Programs\SQL2008\MSSQL10.MSSQLSERVER\MSSQL\DATA

--身份
CREATE TABLE tb_identity(
identityId INT identity(1,1) PRIMARY KEY, --自动增长
_identity NVARCHAR(10) NOT NULL
);

--系部
CREATE TABLE tb_department(
departmentId INT identity(1,1) PRIMARY KEY, --自动增长
_department NVARCHAR(10) NOT NULL
);

--班级
CREATE TABLE tb_class(
classId INT identity(1,1) PRIMARY KEY, --自动增长
_class NVARCHAR(20) NOT NULL,
departmentId int NOT NULL,
FOREIGN KEY(departmentId) references tb_department(departmentId) --外键
);

--学生表
CREATE TABLE tb_student(
	_id varchar(8) PRIMARY KEY, --主键
	name nvarchar(8) NOT NULL,
	age int check(age>0 and age<150) NULL, --检查约束
	sex char(2) default '男' check (sex in ('男','女')), --检查约束
	exerciseTimes int default 0,	--默认约束
	pwd nvarchar(20) default '123456',
	fk_identityId int default 1,
	fk_classId int NOT NULL,
	FOREIGN KEY(fk_identityId) references tb_identity(identityId), --外键 tb_identity表
	FOREIGN KEY(fk_classId) references tb_class(classId) --外键 tb_class表
);

--教师/管理员 表
CREATE TABLE tb_teacher(
	_id varchar(10) PRIMARY KEY, --主键
	name nvarchar(8)  NOT NULL,
	age int check(age>0 and age<150) NULL, --检查约束
	sex char(2) default '男' check (sex in ('男','女')), --检查约束
	pwd nvarchar(20) NOT NULL,
	fk_identityId int default 2, --默认约束
	fk_departmentId int NOT NULL,
	FOREIGN KEY(fk_identityId) references tb_identity(identityId), --外键 tb_identity表
	FOREIGN KEY(fk_departmentId) references tb_department(departmentId) --外键 tb_department表
);

--添加学生
insert into tb_student(_id,name,age,sex,pwd,fk_classId) values ('51340142','朱豪凯',21,'男','123456',1)
insert into tb_student(_id,name,age,sex,pwd,fk_classId) values ('51340143','王洋',21,'男','123456',1)
insert into tb_student(_id,name,age,sex,pwd,fk_classId) values ('51340144','王晓峰',20,'男','123456',1)
insert into tb_student(_id,name,age,sex,pwd,fk_classId) values ('51110142','秦莞尔',20,'女','123456',5)
insert into tb_student(_id,name,age,sex,pwd,fk_classId) values ('51220142','松岛枫',21,'男','123456',7)
insert into tb_student(_id,name,age,sex,pwd,fk_classId) values ('51330142','刘克军',20,'男','123456',9)
insert into tb_student(_id,name,age,sex,pwd,fk_classId) values ('51440142','胖大海',22,'男','123456',11)
--添加教师
insert into tb_teacher(_id,name,age,sex,pwd,fk_departmentId) values ('1100','周杰伦',36,'男','123456',1)
insert into tb_teacher(_id,name,age,sex,pwd,fk_departmentId) values ('2100','杨幂',29,'女','123456',2)
insert into tb_teacher(_id,name,age,sex,pwd,fk_departmentId) values ('3100','张杰',32,'男','123456',3)
insert into tb_teacher(_id,name,age,sex,pwd,fk_departmentId) values ('4100','范冰冰',34,'女','123456',4)
--添加管理员
insert into tb_teacher(_id,name,age,sex,pwd,fk_identityId,fk_departmentId) values ('001','马云',51,'男','123456',3,1)
insert into tb_teacher(_id,name,age,sex,pwd,fk_identityId,fk_departmentId) values ('002','马化腾',44,'男','123456',3,2)
insert into tb_teacher(_id,name,age,sex,pwd,fk_identityId,fk_departmentId) values ('003','李彦宏',47,'男','123456',3,3)
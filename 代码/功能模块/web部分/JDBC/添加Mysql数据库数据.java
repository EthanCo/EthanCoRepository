登陆mysql -u root -p
创建数据库 create database day13;
切换数据库 use day13;
创建数据表
create table users(
   id int primary key not null ,
   name varchar(40),
   pwd varchar(40),
   email varchar(100)
);

向数据表插入几条数据
insert into users values(1,'aaa','111','aaa@itcast.cn');
insert into users values(2,'bbb','111','bbb@itcast.cn');
insert into users values(3,'ccc','111','ccc@itcast.cn');
insert into users values(4,'ddd','111','ddd@itcast.cn');

查看
select * from users;
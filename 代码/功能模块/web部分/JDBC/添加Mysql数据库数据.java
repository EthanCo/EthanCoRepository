��½mysql -u root -p
�������ݿ� create database day13;
�л����ݿ� use day13;
�������ݱ�
create table users(
   id int primary key not null ,
   name varchar(40),
   pwd varchar(40),
   email varchar(100)
);

�����ݱ���뼸������
insert into users values(1,'aaa','111','aaa@itcast.cn');
insert into users values(2,'bbb','111','bbb@itcast.cn');
insert into users values(3,'ccc','111','ccc@itcast.cn');
insert into users values(4,'ddd','111','ddd@itcast.cn');

�鿴
select * from users;
SQL常用语句一览
(1)数据记录筛选：
select [distinct] * from 数据表 where 字段名 = 字段值 order by 字段名 [desc]
select * from 数据表 where 字段名 [not] like '%字段值%' order by 字段名 [desc] (%任意长度,_任意一个字符)
select top10 * from 数据表 where [字段名 > 字段值] order by 字段名 [desc]
select count(*) from 数据表 where [字段名 > 字段值] by 字段名 [desc]
select sum(列1),sum(列2),sum(列3+列4) from 数据表 by 字段名 [desc] //只能对一列 同理的还有max(),min()
select 列1,列2,列3 from 表 group by column having sum(price)>100 //group by 进行分组 having 进行过滤
select * from 数据表 where 字段名 in ('值1','值2','值3')
select * from 数据表 where 字段名 between 值1 and 值2
嵌套查询 求语文最高分是谁？
select name from scores where chinese = (select max(chinese) from scores)
(2)更新数据记录：
update 数据表 set 字段名 = 字段值 where 条件表达式
update 数据表 set 字段1=值1 , 字段2=值2……字段n=值n where 条件表达式
(3)删除数据记录：
delete from 数据表 where 条件表达式
delete from 数据表(将数据表所有记录删除)
(4)添加数据记录：
insert into 数据表(字段1,字段2,字段3…) values (值1,值2,值3…)
insert into 目标数据表 select * from 源数据表(把源数据表的记录添加到目标数据表)


创建数据库
CREATE DATABASE db_name
删除数据库
DROP DATABASE db_name

创建表
CREATE TABLE person (
number INT(11)[identity(1,1)], //自动编号
name VARCHAR(255),
birthday DATE [unique] [NOT NULL] //not null 不为空 unique 唯一
);

创建表 设置number主键,自动增长
CREATE TABLE person (
number INT(11) [AUTO_INCREMENT],
name VARCHAR(255),
birthday DATE NOT NULL
PRIMARY KEY (number),
FOREIGN KEY (XXX)  //外键约束通常应用另一个表的主键 就不能随便取值,取值是引用存在主键值,不能随便删除数据
);

删除表
DROP TABLE  tbl_name;
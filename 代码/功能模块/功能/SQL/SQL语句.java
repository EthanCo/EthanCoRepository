SQL�������һ��
(1)���ݼ�¼ɸѡ��
select [distinct] * from ���ݱ� where �ֶ��� = �ֶ�ֵ order by �ֶ��� [desc]
select * from ���ݱ� where �ֶ��� [not] like '%�ֶ�ֵ%' order by �ֶ��� [desc] (%���ⳤ��,_����һ���ַ�)
select top10 * from ���ݱ� where [�ֶ��� > �ֶ�ֵ] order by �ֶ��� [desc]
select count(*) from ���ݱ� where [�ֶ��� > �ֶ�ֵ] by �ֶ��� [desc]
select sum(��1),sum(��2),sum(��3+��4) from ���ݱ� by �ֶ��� [desc] //ֻ�ܶ�һ�� ͬ��Ļ���max(),min()
select ��1,��2,��3 from �� group by column having sum(price)>100 //group by ���з��� having ���й���
select * from ���ݱ� where �ֶ��� in ('ֵ1','ֵ2','ֵ3')
select * from ���ݱ� where �ֶ��� between ֵ1 and ֵ2
Ƕ�ײ�ѯ ��������߷���˭��
select name from scores where chinese = (select max(chinese) from scores)
(2)�������ݼ�¼��
update ���ݱ� set �ֶ��� = �ֶ�ֵ where �������ʽ
update ���ݱ� set �ֶ�1=ֵ1 , �ֶ�2=ֵ2�����ֶ�n=ֵn where �������ʽ
(3)ɾ�����ݼ�¼��
delete from ���ݱ� where �������ʽ
delete from ���ݱ�(�����ݱ����м�¼ɾ��)
(4)������ݼ�¼��
insert into ���ݱ�(�ֶ�1,�ֶ�2,�ֶ�3��) values (ֵ1,ֵ2,ֵ3��)
insert into Ŀ�����ݱ� select * from Դ���ݱ�(��Դ���ݱ�ļ�¼��ӵ�Ŀ�����ݱ�)


�������ݿ�
CREATE DATABASE db_name
ɾ�����ݿ�
DROP DATABASE db_name

������
CREATE TABLE person (
number INT(11)[identity(1,1)], //�Զ����
name VARCHAR(255),
birthday DATE [unique] [NOT NULL] //not null ��Ϊ�� unique Ψһ
);

������ ����number����,�Զ�����
CREATE TABLE person (
number INT(11) [AUTO_INCREMENT],
name VARCHAR(255),
birthday DATE NOT NULL
PRIMARY KEY (number),
FOREIGN KEY (XXX)  //���Լ��ͨ��Ӧ����һ��������� �Ͳ������ȡֵ,ȡֵ�����ô�������ֵ,�������ɾ������
);

ɾ����
DROP TABLE  tbl_name;
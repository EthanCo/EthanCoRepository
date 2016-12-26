--����1��
 --������ SQL Server 2000/2005
 
SELECT TOP ҳ��С *
 FROM table1
 WHERE id NOT IN
           (
           SELECT TOP ҳ��С*(ҳ��-1) id FROM table1 ORDER BY id
           )
 ORDER BY id
 
--����2��
 --������ SQL Server 2000/2005
 
SELECT TOP ҳ��С *
 FROM table1
 WHERE id >
           (
           SELECT ISNULL(MAX(id),0) 
           FROM 
                 (
                 SELECT TOP ҳ��С*(ҳ��-1) id FROM table1 ORDER BY id
                 ) A
           )
 ORDER BY id
 
--����3��
 --������ SQL Server 2005
 
SELECT TOP ҳ��С * 
 FROM 
         (
         SELECT ROW_NUMBER() OVER (ORDER BY id) AS RowNumber,* FROM table1
         ) A
 WHERE RowNumber > ҳ��С*(ҳ��-1)
 

--˵����ҳ��С��ÿҳ��������ҳ�����ڼ�ҳ��ʹ��ʱ����ѡ�ҳ��С���͡�ҳ��С*(ҳ��-1)���滻�����֡�
 
 
 
 
 
 
 
--�����ķ��������û����������������ʱ��Ҳ�����÷�������������Ч�ʻ�͡�
 --�����Ż���ʱ�򣬼�����������������ѯЧ�ʻ���ߡ�
 
--ͨ��SQL ��ѯ����������ʾ�Ƚϣ��ҵĽ�����:
 --��ҳ��������(����ID���ڶ��ٺ�SELECT TOP��ҳ��Ч����ߣ���Ҫƴ��SQL���
 --��ҳ����һ��(����Not In��SELECT TOP��ҳ)   Ч�ʴ�֮����Ҫƴ��SQL���
 --��ҳ��������(����SQL���α�洢���̷�ҳ)    Ч����������Ϊͨ��
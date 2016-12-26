--方法1：
 --适用于 SQL Server 2000/2005
 
SELECT TOP 页大小 *
 FROM table1
 WHERE id NOT IN
           (
           SELECT TOP 页大小*(页数-1) id FROM table1 ORDER BY id
           )
 ORDER BY id
 
--方法2：
 --适用于 SQL Server 2000/2005
 
SELECT TOP 页大小 *
 FROM table1
 WHERE id >
           (
           SELECT ISNULL(MAX(id),0) 
           FROM 
                 (
                 SELECT TOP 页大小*(页数-1) id FROM table1 ORDER BY id
                 ) A
           )
 ORDER BY id
 
--方法3：
 --适用于 SQL Server 2005
 
SELECT TOP 页大小 * 
 FROM 
         (
         SELECT ROW_NUMBER() OVER (ORDER BY id) AS RowNumber,* FROM table1
         ) A
 WHERE RowNumber > 页大小*(页数-1)
 

--说明，页大小：每页的行数；页数：第几页。使用时，请把“页大小”和“页大小*(页数-1)”替换成数字。
 
 
 
 
 
 
 
--其它的方案：如果没有主键，可以用临时表，也可以用方案三做，但是效率会低。
 --建议优化的时候，加上主键和索引，查询效率会提高。
 
--通过SQL 查询分析器，显示比较：我的结论是:
 --分页方案二：(利用ID大于多少和SELECT TOP分页）效率最高，需要拼接SQL语句
 --分页方案一：(利用Not In和SELECT TOP分页)   效率次之，需要拼接SQL语句
 --分页方案三：(利用SQL的游标存储过程分页)    效率最差，但是最为通用
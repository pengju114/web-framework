/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pj.jdbc.core;

import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author PENGJU
 * email:pengju114@163.com
 * 时间:2012-7-15 21:42:41
 */
public class JdbcTemplate {
    private String id;
    private static Logger logger=Logger.getLogger(JdbcTemplate.class);
    
    public JdbcTemplate(){
        this(SessionFactory.DEFAULT_SESSION);
    }
    public JdbcTemplate(String id){
        this.id=id;
    }
    
    private static void log(String err,Throwable e){
        logger.error(err, e);
    }
    
    private void closeSession(Session s){
        if (s!=null) {
            try {
                s.close();
            } catch (Exception e) {
                log(e.getMessage(), e);
            }
        }
    }
    
    /**
     * 批量执行 sql 脚本
     * @param sql 可带参数的SQL语句
     * @param values 参数值列表
     * @return 影响行数
     * @throws SQLException 
     */
    public int executeBatch(String sql,List<Object[]> values){
        int c=0;
        Session session=getSession();
        if (session!=null) {
            try {
                c=session.executeBatch(sql, values);
            } catch (Exception e) {
                log(e.getMessage(), e);
            } finally{
                closeSession(session);
            }
        }
        
        return c;
    }
    
    /**
     * 执行更新操作
     * @param sql
     * @param paramVals 参数值
     * @return
     * @throws SQLException 
     */
    public int executeUpdate(String sql,Object[] paramVals){
        int c=0;
        Session session=getSession();
        if (session!=null) {
            try {
                c=session.executeUpdate(sql, paramVals);
            } catch (Exception e) {
                log(e.getMessage(), e);
            } finally{
                closeSession(session);
            }
        }
        
        return c;
        
    }
    /**
     * 执行更新
     * @param sql
     * @return
     * @throws SQLException 
     */
    public int executeUpdate(String sql){
        int c=0;
        Session session=getSession();
        if (session!=null) {
            try {
                c=session.executeUpdate(sql);
            } catch (Exception e) {
                log(e.getMessage(), e);
            } finally{
                closeSession(session);
            }
        }
        
        return c;
    }
    /**
     * 按参数执行查询
     * @param sql
     * @param paramVals
     * @return
     * @throws SQLException 
     */
    public ResultList<ResultRow> executeQuery(String sql,Object[] paramVals){
        ResultList<ResultRow> rs=null;
        Session session=getSession();
        if (session!=null) {
            try {
                rs=session.executeQuery(sql, paramVals);
            } catch (Exception e) {
                log(e.getMessage(), e);
            } finally{
                closeSession(session);
            }
        }
        return rs;
    }
    /**
     * 执行查询
     * @param sql
     * @return
     * @throws SQLException 
     */
    public ResultList<ResultRow> executeQuery(String sql){
        ResultList<ResultRow> rs=null; 
        Session session=getSession();
        if (session!=null) {
            try {
                rs=session.executeQuery(sql);
            } catch (Exception e) {
                log(e.getMessage(), e);
            } finally{
                closeSession(session);
            }
        }
        
        return rs;
    }
    /**
     * 按参数执行有限数据行查询
     * @param sql
     * @param paramVals
     * @param startRow 起始行下标 从1开始
     * @param rowCount 需要的结果行数
     * @return 结果列表
     * @throws SQLException 
     */
    public ResultList<ResultRow> executeQuery(String sql,Object[] paramVals,int startRow,int rowCount) {
        ResultList<ResultRow> rs=null;
        Session session=getSession();
        if (session!=null) {
            try {
                rs=session.executeQuery(sql, paramVals, startRow, rowCount);
            } catch (Exception e) {
                log(e.getMessage(), e);
            } finally{
                closeSession(session);
            }
        }
        
        return rs;
    }

    /**
     * 按参数执行有限数据行查询,并将每一行结果行交给filter包装
     * @param sql
     * @param paramVals
     * @param startRow 起始行下标 从1开始
     * @param rowCount 需要的结果行数
     * @param filter 结果集过滤器,可以在这里对记录行进行包装,返回包装的对象即可
     * @return
     * @throws SQLException 
     */
    public <T> ResultList<T> executeQuery(String sql,Object[] paramVals,int startRow,int rowCount,QueryFilter<T> filter){
        ResultList<T> rs=null;
        Session session=getSession();
        if (session!=null) {
            try {
                rs=session.executeQuery(sql, paramVals, startRow, rowCount, filter);
            } catch (Exception e) {
                log(e.getMessage(), e);
            } finally{
                closeSession(session);
            }
        }
        
        return rs;
    }
    /**
     * 按参数执行数据查询,并将每一行结果行交给filter包装
     * @param <T> 动态类型
     * @param sql
     * @param paramVals
     * @param filter 结果集过滤器,可以在这里对记录行进行包装,返回包装的对象即可
     * @return
     * @throws SQLException 
     */
    public <T> ResultList<T> executeQuery(String sql,Object[] paramVals,QueryFilter<T> filter){
        ResultList<T> rs=null;
        Session session=getSession();
        if (session!=null) {
            try {
                rs=session.executeQuery(sql, paramVals, filter);
            } catch (Exception e) {
                log(e.getMessage(), e);
            } finally{
                closeSession(session);
            }
        }
        
        return rs;
    }
    /**
     * 按参查询,并将每一行结果行交给filter包装
     * @param <T> 动态类型
     * @param sql
     * @param filter 结果集过滤器,可以在这里对记录行进行包装,返回包装的对象即可
     * @return
     * @throws SQLException 
     */
    public <T> ResultList<T> executeQuery(String sql,QueryFilter<T> filter) {
        ResultList<T> rs=null;
        Session session=getSession();
        if (session!=null) {
            try {
                rs=session.executeQuery(sql, filter);
            } catch (Exception e) {
                log(e.getMessage(), e);
            } finally{
                closeSession(session);
            }
        }
        
        return rs;
    }
    /**
     * 返回会话对象
     * @return 
     */
    public Session getSession(){
        try {
            return SessionFactory.getSession(id);
        } catch (Exception e) {
            log(e.getMessage(), e);
        }
        return  null;
    }

    /**
     * 保存一行数据
     * 数据的类型必须要和数据库一一对应
     * @param table 表名
     * @param row 数据行
     * @return
     * @throws SQLException 
     */
    public int save(String table,ResultRow row){
        int c=0;
        Session session=getSession();
        if (session!=null) {
            try {
                c=session.save(table, row);
            } catch (Exception e) {
                log(e.getMessage(), e);
            } finally{
                closeSession(session);
            }
        }
        
        return c;
    }
    /**
     * 保存一个对象,必须用@Table对类注解,并且对每一个需要存到数据库的实例变量也要用@Column注解
     * 在此只能保存自己的属性,不能保存继承来的属性
     * 如果没有对类注解则用类名做表名,没有注解的实例变量将不存到数据库中
     * 变量的类型必须和数据库中的类型一一对应
     * @param object
     * @return
     * @throws SQLException 
     */
    public int save(Object object){
        int c=0;
        Session session=getSession();
        if (session!=null) {
            try {
                c=session.save(object);
            } catch (Exception e) {
                log(e.getMessage(), e);
            } finally{
                closeSession(session);
            }
        }
        
        return c;
    }
    /**
     * 查询数据库,并将结果行转换为指定的clazz对象
     * @param <T> 目标类型
     * @param sql sql语句
     * @param paramVals 参数值
     * @param startRow 起始行下标,从1开始
     * @param rowCount 结果行数
     * @param clazz 类型
     * @return
     * @throws SQLException 
     */
    public <T> ResultList<T> executeQuery(String sql,Object[] paramVals,int startRow,int rowCount,Class<T> clazz){
        ResultList<T> rs=null;
        Session session=getSession();
        if (session!=null) {
            try {
                rs=session.executeQuery(sql, paramVals, startRow, rowCount, clazz);
            } catch (Exception e) {
                log(e.getMessage(), e);
            } finally{
                closeSession(session);
            }
        }
       
        return rs;
    }
    /**
     * 查询数据库,并将结果行转换为指定的clazz对象
     * @param <T> 目标类型
     * @param sql sql语句
     * @param paramVals 参数值
     * @param clazz 类型
     * @return
     * @throws SQLException 
     */
    public <T> ResultList<T> executeQuery(String sql,Object[] paramVals,Class<T> clazz) {
        ResultList<T> rs=null;
        Session session=getSession();
        if (session!=null) {
            try {
                rs=session.executeQuery(sql, paramVals, clazz);
            } catch (Exception e) {
                log(e.getMessage(), e);
            } finally{
                closeSession(session);
            }
        }
        
        return rs;
    }
    /**
     * 查询数据库,并将结果行转换为指定的clazz对象
     * @param <T> 目标类型
     * @param sql sql语句
     * @param clazz 类型
     * @return
     * @throws SQLException 
     */
    public <T> ResultList<T> executeQuery(String sql,Class<T> clazz){
        ResultList<T> rs=null;
        Session session=getSession();
        if (session!=null) {
            try {
                rs=session.executeQuery(sql, clazz);
            } catch (Exception e) {
                log(e.getMessage(), e);
            } finally{
                closeSession(session);
            }
        }
        
        return rs;
    }
    
    
    /**
     * 执行查询，并返回一个结果(即使结果有多个)，无结果返回null
     * @param <T> 指定的返回类型
     * @param sql SQL语句
     * @param clazz 指定的类
     * @return T
     */
    public <T> T querySingle(String sql,Class<T> clazz) throws SQLException {
        return querySingle(sql, null, clazz);
    }
    /**
     * 执行查询，并返回一个结果(即使结果有多个)，无结果返回null
     * @param <T> 指定的返回类型
     * @param sql SQL语句,参数用问号表示
     * @param paramVals 参数值
     * @param clazz 指定的类
     * @return T
     */
    public <T> T querySingle(String sql,Object[] paramVals,Class<T> clazz) throws SQLException {
        T val=null;
        Session session=getSession();
        if (session!=null) {
            try {
                val=session.querySingle(sql, paramVals, clazz);
            } catch (Exception e) {
                log(e.getMessage(), e);
            } finally{
                closeSession(session);
            }
        }
        
        return val;
    }
    /**
     * 执行查询，并返回一个结果(即使结果有多个)，无结果返回null
     * @param sql SQL语句
     * @return ResultRow
     */
    public ResultRow querySingle(String sql) throws SQLException {
        return querySingle(sql, new Object[0]);
    }
    /**
     * 执行查询，并返回一个结果(即使结果有多个)，无结果返回null
     * @param sql SQL语句,参数用问号表示
     * @param paramVals 参数值
     * @return ResultRow
     */
    public ResultRow querySingle(String sql,Object[] paramVals) throws SQLException{
        ResultRow val=null;
        Session session=getSession();
        if (session!=null) {
            try {
                val=session.querySingle(sql, paramVals);
            } catch (Exception e) {
                log(e.getMessage(), e);
            } finally{
                closeSession(session);
            }
        }
        
        return val;
    }
    
    public ResultList<ResultRow> list(String table){
        ResultList<ResultRow> val=null;
        Session session=getSession();
        if (session!=null) {
            try {
                val=session.list(table);
            } catch (Exception e) {
                log(e.getMessage(), e);
            } finally{
                closeSession(session);
            }
        }
        
        return val;
    }
    
    
    public ResultList<ResultRow> list(String table, int startRow, int rowCount) {
        ResultList<ResultRow> val=null;
        Session session=getSession();
        if (session!=null) {
            try {
                val=session.list(table, startRow, rowCount);
            } catch (Exception e) {
                log(e.getMessage(), e);
            } finally{
                closeSession(session);
            }
        }
        
        return val;
    }
}

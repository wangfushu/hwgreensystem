package com.xmrbi.hwgreensystem.dao.util;

import com.xmrbi.hwgreensystem.common.MessageConfigConstant;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangfs on 2018-03-28 helloword.
 */
public class IdUtilDao {
    //private DataSource dataSource;

    private String identityTable = "sys_seq_table";

    /**
     * 根据实体名称获取最新的滚动号，并且自动按照递增长度设置最后的滚动号
     *
     * @param entityName
     * @param size
     * @return
     */
    public long getLastNumber(String entityName, int size) {
        //从数据库取得记录
        Connection connection = null;
        long _latestNumber = 1;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("entityName", entityName);
        try {
            connection =getConnection(url);
            connection.setAutoCommit(false);
            Long lastestNumber = getLastestNumber(connection, params);
            if (lastestNumber != null) {
                _latestNumber = lastestNumber;
            }
            //如果查询不到任何结果就插入一条新的记录
            else {
                insertNewRecord(connection, params);
            }
            params.put("newNumber", _latestNumber + size);
            //更新最后的记录
            updateRecord(connection, params);
            //提交事务
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    //log.error(e.getMessage(), e1);
                }
            }
            //log.error(e.getMessage(), e);
        } finally {
            clearConnection(connection);
        }
        return _latestNumber;
    }

    /**
     * 获取当前表的最后主键序列
     *
     * @param connection
     * @return
     * @throws java.sql.SQLException
     */
    private Long getLastestNumber(Connection connection, Map<String, Object> params) throws SQLException {
        String selectSql = "select ST_SeqValue from " + identityTable + " where ST_SeqName = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
        preparedStatement.setString(1, params.get("entityName").toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getLong("ST_SeqValue");
        }
        return null;
    }

    /**
     * 插入一条表的主键记录
     *
     * @param connection
     * @throws java.sql.SQLException
     */
    private void insertNewRecord(Connection connection, Map<String, Object> params) throws SQLException {
        String insertSql = "insert into "
                + identityTable + "( ST_SeqName, ST_SeqValue)" +
                "values(?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
        //preparedStatement.setString(1, params.get("tableName") == null ? null : params.get("tableName").toString());
        preparedStatement.setString(1, params.get("entityName").toString());
        preparedStatement.setLong(2, 1);
        preparedStatement.execute();
    }

    /**
     * 更新一条记录
     *
     * @param connection
     * @param params
     * @throws java.sql.SQLException
     */
    private void updateRecord(Connection connection, Map<String, Object> params) throws SQLException {
        //更新最后的记录
        String updateSql = "update " + identityTable + " set ST_SeqValue = ? where ST_SeqName=?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
        preparedStatement.setLong(1, Long.valueOf(params.get("newNumber").toString()));
        preparedStatement.setString(2, params.get("entityName").toString());
        preparedStatement.execute();
    }

    /**
     * 释放连接资源
     *
     * @param connection
     */
    public void clearConnection(Connection connection) {
        //关闭resultset
//      if (resultSet != null) {
//          try {
//              resultSet.close();
//          } catch (SQLException e) {
//              resultSet = null;
//              e.printStackTrace();
//          }
//      }
//      //关闭preparedStatement
//      if (preparedStatement != null) {
//          try {
//              preparedStatement.close();
//          } catch (SQLException e) {
//              preparedStatement = null;
//              log.error(e.getMessage(), e);
//          }
//      }
        //关闭连接
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                connection = null;
                //log.error(e.getMessage(), e);
            }
        }
    }

/*    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }*/

    public String getIdentityTable() {
        return identityTable;
    }

    public void setIdentityTable(String identityTable) {
        this.identityTable = identityTable;
    }



    private static String DRIVER = MessageConfigConstant.IDUTIL_DRIVER;
    //private static  String url = "jdbc:sqlserver://172.16.54.188:1433;databaseName=Station_cardMaintenance";
    private static String url = MessageConfigConstant.IDUTIL_URL;
    //private static  String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=cardMaintenance";
    //private static  String url = "jdbc:sqlserver://172.20.10.24:1433;databaseName=VYearFee4S";
    /* private static String username = "sa";
     private static String password = "123456";*/
     private static String username = MessageConfigConstant.IDUTIL_USERNAME;;
     private static String password = MessageConfigConstant.IDUTIL_PASSWORD;;
    private static Connection conn = null;

    //连接数据库
    public static Connection getConnection(String url) {
        try{
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(url, username, password);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("数据库驱动加载失败");
        }
        return conn;
    }
}

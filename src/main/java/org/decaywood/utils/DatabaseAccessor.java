package org.decaywood.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author: decaywood
 * @date: 2015/12/1 10:06
 */


/**
 * 数据库线程池
 */
public class DatabaseAccessor {

    public static class Holder {
        public static final DatabaseAccessor ACCESSOR = new DatabaseAccessor();
    }

    private String jdbcDriver = ""; // 数据库驱动
    private String dbUrl = ""; // 数据库 URL
    private String dbUsername = ""; // 数据库用户名
    private String dbPassword = ""; // 数据库用户密码
    private int initialConnections = 10; // 连接池的初始大小
    private int autoIncreaseStep = 5;// 连接池自增步进
    private int maxConnections = 50; // 连接池最大的大小
    private List<PooledConnection> connections = null; // 存放连接池中数据库连接的向量 , 初始时为 null


    public DatabaseAccessor() {
        this(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/XueQiuSpider",
                "root",
                "123456"
        );
    }



    public DatabaseAccessor(String jdbcDriver, String dbUrl, String dbUsername,
                          String dbPassword) {

        this.jdbcDriver = jdbcDriver;
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;

        try {
            createPool();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public synchronized void createPool() throws Exception {

        if (connections != null) return;

        Class.forName(this.jdbcDriver);
        connections = new CopyOnWriteArrayList<>();
        createConnections(this.initialConnections);
        System.out.println(" 数据库连接池创建成功！ ");

    }



    public synchronized Connection getConnection() {

        if (connections == null) return null;
        Connection conn;
        // if no available connection retry
        while (true) {
            wait(250);
            try {
                conn = getFreeConnection();
                break;
            } catch (Exception e) {
                System.out.println("线程池忙，等待空闲线程");
            }
        }
        return conn;// 返回获得的可用的连接

    }



    public void returnConnection(Connection conn) {

        if (connections == null) {
            System.out.println(" 连接池不存在，无法返回此连接到连接池中 !");
            return;
        }

        PooledConnection connection = this.connections.stream()
                .filter(x -> x.getConnection() == conn)
                .findAny()
                .orElse(emptyPooledConn);
        connection.setBusy(false);

    }



    public synchronized void refreshConnections() throws SQLException {

        if (connections == null) {
            System.out.println(" 连接池不存在，无法刷新 !");
            return;
        }

        for (PooledConnection connection : connections) {
            if (connection.isBusy()) wait(5000);
            // 关闭此连接，用一个新的连接代替它。
            closeConnection(connection.getConnection());
            connection.setConnection(newConnection());
            connection.setBusy(false);
        }

    }


    public synchronized void closeConnectionPool() throws SQLException {

        if (connections == null) {
            System.out.println(" 连接池不存在，无法关闭 !");
            return;
        }

        for (PooledConnection connection : connections) {
            if (connection.isBusy()) {
                wait(5000); // 等 5 秒
            }
            closeConnection(connection.getConnection());
            connections.remove(connection);
        }

        connections = null;

    }


    private void createConnections(int numConnections) throws SQLException {

        for (int i = 0; i < numConnections; i++) {
            try {
                if (this.maxConnections > 0 && this.connections.size() >= this.maxConnections) break;
                connections.add(new PooledConnection(newConnection()));
            } catch (SQLException e) {
                System.out.println(" 创建数据库连接失败！ " + e.getMessage());
                throw new SQLException();
            }
            System.out.println(" 数据库连接己创建 ......");
        }
    }



    private Connection newConnection() throws SQLException {

        Connection conn = DriverManager.getConnection(dbUrl, dbUsername,
                dbPassword);

        if (connections.size() == 0) {

            DatabaseMetaData metaData = conn.getMetaData();
            int driverMaxConnections = metaData.getMaxConnections();

            if (driverMaxConnections > 0) {
                this.maxConnections = Math.min(maxConnections, driverMaxConnections);
            }

        }

        return conn;

    }



    private Connection getFreeConnection() throws Exception {

        Connection conn = findFreeConnection();

        if (conn == null) {
            // 如果目前连接池中没有可用的连接,按步进增加连接
            createConnections(autoIncreaseStep);
            conn = findFreeConnection();
        }
        return conn;
    }


    private Connection findFreeConnection() throws Exception {

        PooledConnection connection = connections
                .stream()
                .filter(x -> !x.isBusy())
                .findAny().get();
        connection.setBusy(true);
        return connection.getConnection();

    }


    private void closeConnection(Connection conn) {

        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(" 关闭数据库连接出错： " + e.getMessage());
        }

    }


    private void wait(int mSeconds) {

        try {
            Thread.sleep(mSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 内部使用的用于保存连接池中连接对象的类
     */
    private PooledConnection emptyPooledConn = new PooledConnection(null);

    class PooledConnection {

        private Connection connection = null;// 数据库连接
        private volatile boolean busy = false; // 此连接是否正在使用的标志，默认没有正在使用


        public PooledConnection(Connection connection) {
            this.connection = connection;
        }

        public Connection getConnection() {
            return connection;
        }

        public void setConnection(Connection connection) {
            this.connection = connection;
        }

        public boolean isBusy() {
            return busy;
        }

        public void setBusy(boolean busy) {
            this.busy = busy;
        }

    }


}

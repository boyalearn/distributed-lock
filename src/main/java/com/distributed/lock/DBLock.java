package com.distributed.lock;

import com.distributed.dao.LockDao;
import com.distributed.exception.LockTimeOutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DBLock implements Lock {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(2, 3, 1, TimeUnit.MINUTES, new LinkedBlockingDeque<Runnable>(100), new ThreadPoolExecutor.AbortPolicy());

    private ThreadLocal<Connection> connectThreadLocal = new ThreadLocal<Connection>();

    @Autowired
    private DataSource datasource;

    @Autowired
    private LockDao lockDao;


    @Override
    public boolean lock(String lockName, int timeOut) throws LockTimeOutException {
        return exeLock("SELECT * FROM locktable WHERE lockName='" + lockName + "' for update");
    }


    @Override
    public void unLock(String lockName) {
        Connection connection = connectThreadLocal.get();
        try {
            if (null != connection) {
                connection.commit();
                DataSourceUtils.releaseConnection(connection, datasource);
            }
        } catch (SQLException e) {
            if (null != connection) {
                DataSourceUtils.releaseConnection(connection, datasource);
            }
        }
    }

    private boolean exeLock(String sql) {
        try {
            Connection connection = DataSourceUtils.getConnection(datasource);
            connectThreadLocal.set(connection);
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (null != resultSet) {
                return true;
            }
            connection.rollback();
            DataSourceUtils.releaseConnection(connection, datasource);
            return false;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean tryLock(String lockName) {
        try {
            return exeLock("SELECT * FROM locktable WHERE lockName='" + lockName + "' for update nowait");
        } catch (Exception e) {
            Connection connection = connectThreadLocal.get();
            if (null != connection) {
                DataSourceUtils.releaseConnection(connection, datasource);
            }
            return false;
        }
    }

}

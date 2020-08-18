package com.distributed.lock;

import com.distributed.bean.LockBean;
import com.distributed.dao.LockDao;
import com.distributed.exception.LockTimeOutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class DBLock implements Lock {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private ThreadLocal<TransactionStatus> threadLocal = new ThreadLocal<>();

    @Autowired
    private DataSourceTransactionManager txManager;

    @Autowired
    private LockDao lockDao;


    @Override
    public boolean lock(String lockName, int timeOut) throws LockTimeOutException {
        LockBean lock;
        Exception lockException = null;
        try {
            LockBean lockBean = initTx(lockName, timeOut);

            lock = lockDao.lock(lockBean);
            if (null != lock) {
                return true;
            }
        } catch (Exception e) {
            lockException = e;
            throw new LockTimeOutException("get lock time out");
        } finally {
            if (null != lockException) {
                txManager.rollback(threadLocal.get());
            }
        }
        return false;
    }

    @Override
    public void unLock(String lockName) {
        TransactionStatus status = threadLocal.get();
        if (null != status) {
            threadLocal.remove();
            txManager.rollback(status);
        }
    }

    @Override
    public boolean tryLock(String lockName) {
        LockBean lockBean = initTx(lockName, 60000);
        LockBean lock = lockDao.tryLock(lockBean);
        if (null != lock) {
            return true;
        } else {
            return false;
        }

    }


    private LockBean initTx(String lockName, Integer timeOut) {
        //设置事务隔离级别相关属性
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        //获取锁属性,如果业务方法参数没有LockBean对象，那么使用默认当前lockBean
        LockBean lockBean = new LockBean(lockName, timeOut);
        //数据库锁实现方式采用行锁。所以必须保证数据库中有这条数据来锁定.
        //ensureLockExits(lockBean, definition);
        LOGGER.debug("do logic");
        definition.setTimeout(timeOut);
        TransactionStatus status = txManager.getTransaction(definition);
        threadLocal.set(status);
        return lockBean;
    }
}

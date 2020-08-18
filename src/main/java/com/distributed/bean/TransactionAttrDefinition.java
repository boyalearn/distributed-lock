package com.distributed.bean;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

public class TransactionAttrDefinition {
    private TransactionDefinition definition;

    private TransactionStatus status;

    private DataSourceTransactionManager txManager;

    public TransactionAttrDefinition(TransactionDefinition definition, TransactionStatus status, DataSourceTransactionManager txManager) {
        this.definition = definition;
        this.status = status;
        this.txManager = txManager;
    }

    public TransactionDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(TransactionDefinition definition) {
        this.definition = definition;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public DataSourceTransactionManager getTxManager() {
        return txManager;
    }

    public void setTxManager(DataSourceTransactionManager txManager) {
        this.txManager = txManager;
    }
}

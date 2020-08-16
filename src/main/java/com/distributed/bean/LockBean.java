package com.distributed.bean;

public class LockBean {
    private String lockName;

    private Integer timeOut;

	public LockBean(String lockName, Integer timeOut) {
		this.lockName = lockName;
		this.timeOut = timeOut;
	}

	public LockBean(String lockName) {
		this.lockName = lockName;
	}

	public String getLockName() {
		return lockName;
	}

	public void setLockName(String lockName) {
		this.lockName = lockName;
	}

	public Integer getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}
}

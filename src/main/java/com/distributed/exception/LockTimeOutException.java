package com.distributed.exception;

public class LockTimeOutException extends Exception{

	private static final long serialVersionUID = -2802676051478872671L;
	
	public LockTimeOutException(String msg){
		super(msg);
	}

}

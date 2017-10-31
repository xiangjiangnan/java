package com.xjn.web.exception;

public class CannotVoteException extends Exception{
	public CannotVoteException(){
		System.out.println("一分钟内不允许重复投票");
	}
}
package com.xjn.web.exception;

public class LimitVoteException extends Exception{
	public LimitVoteException(){
		System.out.println("投票数限制在100以内");
	}
}


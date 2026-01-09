package com.personal.myblog.exception;

public class PostNotFoundException extends RuntimeException{

	public PostNotFoundException(String message) {
		super(message);
	}
}

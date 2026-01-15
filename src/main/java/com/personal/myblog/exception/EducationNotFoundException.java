package com.personal.myblog.exception;

public class EducationNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public EducationNotFoundException(String message) {
		super(message);
	}
}

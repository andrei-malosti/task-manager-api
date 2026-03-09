package com.taskmanager.domain.exception;

public class TaskNotFoundException extends ResourceNotFoundException{
	
	private static final long serialVersionUID = 1L;

	public TaskNotFoundException(String msg) {
		super(msg);
	}

}

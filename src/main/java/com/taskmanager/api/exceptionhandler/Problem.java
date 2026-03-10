package com.taskmanager.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Problem {
	
	private Integer status;
	private String type;
	private String title;
	private String detail;
	
	private OffsetDateTime timeStamp;
	
	private List<Problem.Field> fields;
	
	@Getter
	@Builder
	public static class Field {
		private String fieldName;
		private String fieldMessage;
	}
	
}

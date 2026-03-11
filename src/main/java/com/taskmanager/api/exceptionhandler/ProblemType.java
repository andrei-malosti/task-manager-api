package com.taskmanager.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	RESOURCE_NOT_FOUND("resource-not-found", "Resource not found"),
	RESOURCE_IN_USE("resource-in-use", "Resource in use"),
	ENDPOINT_NOT_FOUND("endpoint-not-found", "Endpoint does not exist"),
	INVALID_DATA("invalid-data", "One or more fields are invalid"),
	INVALID_PATH_VALUE("invalid-path-value", "Invalid value for parameter"),
	INVALID_REQUEST_BODY("invalid-request-body", "Invalid request body");
	
	private String uri;
	private String title;
	
	ProblemType(String uri, String title){
		this.uri = "https://taskmanager.com/" + uri;
		this.title = title;
	}
	
}

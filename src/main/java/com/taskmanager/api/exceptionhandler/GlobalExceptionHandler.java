package com.taskmanager.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.taskmanager.domain.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import tools.jackson.core.JacksonException.Reference;
import tools.jackson.databind.exc.InvalidFormatException;
import tools.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	private final MessageSource messageSource;

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request){
		
		HttpStatusCode status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		Problem problem = createProblemBuilder(status, problemType, ex.getMessage())
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleResourceInUse(DataIntegrityViolationException ex, WebRequest request){
	
		HttpStatusCode status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.RESOURCE_IN_USE;
		Problem problem = createProblemBuilder(status, problemType, "Resource is in use and cannot be deleted.")
				.build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected @Nullable ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		HttpStatusCode statusCode = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ENDPOINT_NOT_FOUND;
		Problem problem = createProblemBuilder(statusCode, problemType, "Endpoint does not exist.")
				.build();
		
		return handleExceptionInternal(ex, problem, headers, statusCode, request);
	}
	
	@Override
	protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		BindingResult bindingResult = ex.getBindingResult();
		List<Problem.Field> fields = bindingResult.getFieldErrors().stream()
				.map(fieldError -> { 
				
					String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
					
					return Problem.Field.builder()
							.fieldName(fieldError.getField())
							.fieldMessage(message)
							.build();
				}).toList();
		
		ProblemType problemType = ProblemType.INVALID_DATA;
		Problem problem = createProblemBuilder(status, problemType, "One or more fields are invalid.")
				.fields(fields)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected @Nullable ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		
		String detail = String.format("The value %s is not valid for parameter %s. Please check the format and try again."
				,ex.getValue(), ex.getRequiredType().getSimpleName());
		ProblemType problemType = ProblemType.INVALID_PATH_VALUE;
		Problem problem = createProblemBuilder(status, problemType, detail)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected @Nullable ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		Throwable rootCause = NestedExceptionUtils.getRootCause(ex);
		
		if(rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
		} else if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		}
		
		String detail = "The request body is invalid or malformed. Check for syntax errors.";
		ProblemType problemType = ProblemType.INVALID_REQUEST_BODY;
		Problem problem = createProblemBuilder(status, problemType, detail)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex,HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		String path = joinPath(ex.getPath());
		String detail = String.format("property %s does not exist", path);
		ProblemType problemType = ProblemType.INVALID_REQUEST_BODY;
		Problem problem = createProblemBuilder(status, problemType, detail)
				.build();
	
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
		String path = joinPath(ex.getPath());
		String detail = String.format("property %s does not accept %s. Expected type: %s"
				, path.toUpperCase(), String.valueOf(ex.getValue()).toUpperCase(), ex.getTargetType().getSimpleName().toUpperCase());
		
		ProblemType problemType = ProblemType.INVALID_REQUEST_BODY;
		Problem problem = createProblemBuilder(status, problemType, detail)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatusCode status, ProblemType problemType, String detail){
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail)
				.timeStamp(OffsetDateTime.now());
	}
	
	private String joinPath(List<Reference> ref) {
		return ref.stream()
				.map(reference -> reference.getPropertyName())
				.collect(Collectors.joining("."));
	}
}

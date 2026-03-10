package com.taskmanager.api.dto;

import java.time.OffsetDateTime;

import com.taskmanager.domain.enums.TaskStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskRequestDTO(
		@NotBlank
		@Size(min = 6, max = 40) 
		String title,
		
		@Size(min = 0, max = 120)
		String description,
		
		@NotNull
		TaskStatus status,
		
		@NotNull
		@FutureOrPresent
		OffsetDateTime dueDate,
		
		@NotNull
		@Valid
		UserUniqueIdDTO user
		) {
	
}

package com.taskmanager.api.dto;

import java.time.OffsetDateTime;

import com.taskmanager.domain.enums.TaskStatus;

public record TaskResponseDTO(
		Long id,
		String title,
		String description,
		OffsetDateTime createdAt,
		OffsetDateTime dueDate,
		TaskStatus status,
		UserResponseDTO user
		) {

}

package com.taskmanager.api.dto;

import java.time.OffsetDateTime;

public record UserResponseDTO(
		Long id,
		String name,
		String email,
		OffsetDateTime createdAt
		) {

}

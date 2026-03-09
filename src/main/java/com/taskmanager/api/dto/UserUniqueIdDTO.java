package com.taskmanager.api.dto;

import jakarta.validation.constraints.NotNull;

public record UserUniqueIdDTO(
		@NotNull Long id) {

}

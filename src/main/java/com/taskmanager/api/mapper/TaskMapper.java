package com.taskmanager.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.taskmanager.api.dto.TaskRequestDTO;
import com.taskmanager.api.dto.TaskResponseDTO;
import com.taskmanager.domain.entity.Task;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

	Task toEntity(TaskRequestDTO dto);
	
	TaskResponseDTO toResponseDTO(Task task);
	
}

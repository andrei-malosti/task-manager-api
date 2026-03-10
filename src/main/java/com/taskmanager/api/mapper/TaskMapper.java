package com.taskmanager.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.taskmanager.api.dto.TaskRequestDTO;
import com.taskmanager.api.dto.TaskResponseDTO;
import com.taskmanager.domain.entity.Task;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

	Task toEntity(TaskRequestDTO dto);
	
	TaskResponseDTO toResponseDTO(Task task);
	
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	void updateTaskFromDTO(@MappingTarget Task task, TaskRequestDTO dto);
	
}

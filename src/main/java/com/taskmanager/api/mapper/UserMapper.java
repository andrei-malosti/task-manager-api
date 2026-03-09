package com.taskmanager.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.taskmanager.api.dto.UserRequestDTO;
import com.taskmanager.api.dto.UserResponseDTO;
import com.taskmanager.domain.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

	User toEntity(UserRequestDTO dto);
	
	UserResponseDTO toResponseDTO(User user);
	
	void updateEntityFromDTO(@MappingTarget User user, UserRequestDTO dto);
}

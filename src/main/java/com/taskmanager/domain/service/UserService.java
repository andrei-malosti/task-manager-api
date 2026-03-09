package com.taskmanager.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskmanager.api.dto.UserRequestDTO;
import com.taskmanager.api.dto.UserResponseDTO;
import com.taskmanager.api.mapper.UserMapper;
import com.taskmanager.domain.entity.User;
import com.taskmanager.domain.exception.UserNotFoundException;
import com.taskmanager.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository repository;
	private final UserMapper mapper;
	
	public List<UserResponseDTO> findAll(){
		return repository.findAll().stream().map(mapper::toResponseDTO).toList();
	}
	
	public UserResponseDTO findById(Long id) {
		return mapper.toResponseDTO(findUserByIdAux(id));
	}
	
	@Transactional
	public UserResponseDTO create(UserRequestDTO dto) {
		User user = mapper.toEntity(dto);
		user = repository.save(user);
		return mapper.toResponseDTO(user);
	}
	
	@Transactional
	public UserResponseDTO update(UserRequestDTO dto, Long id) {
		User user = findUserByIdAux(id);
		mapper.updateEntityFromDTO(user, dto);
		user = repository.save(user);
		return mapper.toResponseDTO(user);
	}
	
	@Transactional
	public void delete(Long id) {
		User user = findUserByIdAux(id);
		repository.delete(user);
	}
	
	private User findUserByIdAux(Long id) {
		return repository.findById(id).orElseThrow(() -> new UserNotFoundException(String.format("User with id %d not found", id)));
	}
}

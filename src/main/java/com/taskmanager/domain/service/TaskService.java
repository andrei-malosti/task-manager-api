package com.taskmanager.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taskmanager.api.dto.TaskRequestDTO;
import com.taskmanager.api.dto.TaskResponseDTO;
import com.taskmanager.api.mapper.TaskMapper;
import com.taskmanager.domain.entity.Task;
import com.taskmanager.domain.entity.User;
import com.taskmanager.domain.exception.TaskNotFoundException;
import com.taskmanager.domain.exception.UserNotFoundException;
import com.taskmanager.domain.repository.TaskRepository;
import com.taskmanager.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

	private final TaskRepository repository;
	private final UserRepository userRepository;
	private final TaskMapper mapper;
	
	public List<TaskResponseDTO> findAll(){
		return repository.findAll().stream().map(mapper::toResponseDTO).toList();
	}
	
	public TaskResponseDTO findById(Long id) {
		return mapper.toResponseDTO(findTaskByIdAux(id));
	}
	
	@Transactional
	public TaskResponseDTO create(TaskRequestDTO dto) {
		Task task = mapper.toEntity(dto);
		User user = findUserByIdAux(dto.user().id());
		task.setUser(user);
		task = repository.save(task);
		return mapper.toResponseDTO(task);
	}
	
	@Transactional
	public TaskResponseDTO update(TaskRequestDTO dto, Long id) {
		Task task = findTaskByIdAux(id);
		User user = findUserByIdAux(dto.user().id());
		mapper.updateTaskFromDTO(task, dto);
		task.setUser(user);
		task = repository.save(task);
		return mapper.toResponseDTO(task);
	}
	
	@Transactional
	public void delete(Long id) {
		Task task = findTaskByIdAux(id);
		repository.delete(task);
	}
	
	private Task findTaskByIdAux(Long id) {
		return repository.findById(id).orElseThrow(() -> new TaskNotFoundException(String.format("Task with id %d not found", id)));
	}
	
	private User findUserByIdAux(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException(String.format("User with id %d not found", id)));
	}
}

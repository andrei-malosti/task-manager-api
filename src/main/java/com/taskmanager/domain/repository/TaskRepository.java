package com.taskmanager.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanager.domain.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

}

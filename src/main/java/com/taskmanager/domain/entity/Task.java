package com.taskmanager.domain.entity;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.taskmanager.domain.enums.TaskStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotBlank
	@Size(min = 6, max = 40)
	@Column(nullable = false)
	private String title;
	
	@Size(min = 0, max = 120)
	private String description;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TaskStatus status;
	
	@CreationTimestamp
	@Column(nullable = false)
	private OffsetDateTime createdAt;
	
	@NotNull
	@FutureOrPresent
	@Column(nullable = false)
	private OffsetDateTime dueDate;
	
	@NotNull
	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;
	
}

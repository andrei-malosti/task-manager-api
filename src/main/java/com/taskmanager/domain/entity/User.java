package com.taskmanager.domain.entity;

import java.time.OffsetDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotBlank
	@Size(min = 3, max = 80)
	@Column(nullable = false)
	private String name;
	
	@NotBlank
	@Email
	@Column(nullable = false)
	private String email;
	
	@NotBlank
	@Size(min = 6, max = 20)
	@Column(nullable = false)
	private String password;
	
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private OffsetDateTime createdAt;
}

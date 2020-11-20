package com.example.technoxtream17.expensetracker.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")
public class User {
	
	@Id 
	@GeneratedValue
	private Long id;
	
	@NotNull(message="name should not null")
	private String name;
	
	@Email(message=" email should be valid")
	private String email;
	
	@OneToMany
	private Set<Category> category;
	

}

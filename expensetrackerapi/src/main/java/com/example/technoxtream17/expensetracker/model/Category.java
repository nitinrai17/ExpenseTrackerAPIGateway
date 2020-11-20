package com.example.technoxtream17.expensetracker.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="category")
public class Category {
	
	@Id 
	@GeneratedValue
	private int categoryId;
	
	@NotNull(message=" name should not null")
	@NotBlank(message=" name should not blank")
	@Size(min=3,message="Name should be atleast 3 character") 
	private String categoryName;

}

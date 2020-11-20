package com.example.technoxtream17.expensetracker.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="expense")
public class Expense {
	
	@Id @GeneratedValue 
	private int id;
	
	@PastOrPresent(message="date should be old or present")
	private Instant expenseDate;
	
	@NotNull(message=" description should not null")
	@NotBlank(message=" description should not blank")
	@Size(min=3,message="description should be atleast 3 character")
	private String description;
	
	@PositiveOrZero(message="amount should not be negative")
	private Double amount;
	
	@JsonIgnore
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Category category;

}

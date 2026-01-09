package com.personal.myblog.post.model;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import com.personal.myblog.education.model.Education;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Past;
import lombok.Data;

@Entity
@Table(name="posts")
@Data
public class PostModel {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private Long id;
	private String name;
	private String address;
	private String image;
	
	// GENDER
	@Enumerated(EnumType.STRING)
	private Gender gender = Gender.MALE; // DEFAULT
	
	// born date and age
	@Past(message="Born Date must be in the past")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate bornDate;
	// no save database2.
	@Transient
	private int age;

	// manytoone
	@ManyToOne
    @JoinColumn(name = "education_id", nullable = false)
    private Education education;
}

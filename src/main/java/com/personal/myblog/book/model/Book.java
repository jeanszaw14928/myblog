package com.personal.myblog.book.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="books")
@Data
public class Book {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private int id;
	private String author;
	@Column(name="publish_date")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate publishDate;
	private String image;
}

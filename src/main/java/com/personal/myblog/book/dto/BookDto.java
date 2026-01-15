package com.personal.myblog.book.dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BookDto {

	private int id;
	private String author;
	private LocalDate publishDate;
	private String image; // old image name
	private MultipartFile file; // new upload
}

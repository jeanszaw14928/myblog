package com.personal.myblog.post.Dto;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import com.personal.myblog.post.model.Gender;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostDto {
	private Long id;
	private String name;
	private String address;
	private String image; // old image name
	private MultipartFile file; // new upload
	private Gender gender;
	private LocalDate bornDate;
	@NotNull(message = "Education must be selected")
	private Integer educationId;
}

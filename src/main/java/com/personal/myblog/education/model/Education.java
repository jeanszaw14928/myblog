package com.personal.myblog.education.model;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import com.personal.myblog.post.model.PostModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="edu")
@Data
public class Education {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private Integer id;
	@NotBlank(message = "Name must not be empty")
	private String name;
	 @OneToMany(mappedBy = "education")
	    private List<PostModel> posts;
}

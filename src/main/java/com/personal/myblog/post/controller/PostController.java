package com.personal.myblog.post.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.personal.myblog.education.model.Education;
import com.personal.myblog.education.repo.EducationRepo;
import com.personal.myblog.image.ImageService;
import com.personal.myblog.post.Dto.PostDto;
import com.personal.myblog.post.model.PostModel;
import com.personal.myblog.post.repo.PostRepo;
import com.personal.myblog.post.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
	// education repo
	private final EducationRepo educationRepo;
	private final PostService postService;
	private final PostRepo postRepo;
	private final ImageService imageService;
	// post service
	@GetMapping("/all")
	public String all(Model model) {
		List<PostModel> posts = postRepo.findAll();
		
		posts.forEach(post -> {
	        int age = postService.calculateAge(post.getBornDate());
	        post.setAge(age);   // transient field
	    });
		
		model.addAttribute("posts",posts);
		return "post/index";
	}
	
	// post create
	@GetMapping("/create")
	public String add(Model model) {
		model.addAttribute("post",new PostDto());
		model.addAttribute("educations",educationRepo.findAll());
		return "post/post_create";
	}
	// post form
	@PostMapping("/add")
	public String create(
	        @Valid @ModelAttribute("post") PostDto postDto,
	        BindingResult result,
	        Model model) {

		if (result.hasErrors()) {
		    model.addAttribute("educations", educationRepo.findAll());
		    return "post/post_create";
		}

	    PostModel post = new PostModel();
	    post.setName(postDto.getName());
	    post.setAddress(postDto.getAddress());
	    post.setGender(postDto.getGender());
	    post.setBornDate(postDto.getBornDate());

	    // education
	    Education edu = educationRepo.findById(postDto.getEducation())
	            .orElseThrow(() -> new IllegalArgumentException("Invalid education id"));
	    post.setEducation(edu);

	 // ပုံကို ImageService သုံးပြီး Save လုပ်ပါ
	    if (postDto.getFile() != null && !postDto.getFile().isEmpty()) {
	        String fileName = imageService.saveFile(postDto.getFile()); // ဒီနေရာမှာ ပုံကို တကယ် သိမ်းလိုက်တာပါ
	        post.setImage(fileName);
	    }

	    postService.create(post);
	    return "redirect:/post/all";
	}

}

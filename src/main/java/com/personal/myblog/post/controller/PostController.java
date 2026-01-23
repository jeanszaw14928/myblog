package com.personal.myblog.post.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.personal.myblog.education.model.Education;
import com.personal.myblog.education.repo.EducationRepo;
import com.personal.myblog.education.service.EducationService;
import com.personal.myblog.image.ImageService;
import com.personal.myblog.image.ImageType;
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
	private final EducationService educationService;
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
		model.addAttribute("pageTitle","Post Page");
		return "post/index";
	}
	
	// post create
	@GetMapping("/create")
	public String add(Model model) {
		model.addAttribute("post",new PostDto());
		model.addAttribute("educations",educationService.all());
		return "post/post_create";
	}
	// post form
	@PostMapping("/add")
	public String create(
	        @Valid @ModelAttribute("post") PostDto postDto,
	        BindingResult result,
	        Model model) throws IOException {

		if (result.hasErrors()) {
		    model.addAttribute("educations", educationService.all());
		    return "post/post_create";
		}

	    PostModel post = new PostModel();
	    post.setName(postDto.getName());
	    post.setAddress(postDto.getAddress());
	    post.setGender(postDto.getGender());
	    post.setBornDate(postDto.getBornDate());

	    // education
	    Education edu = educationRepo.findById(postDto.getEducationId())
	            .orElseThrow(() -> new IllegalArgumentException("Invalid education id"));
	    post.setEducation(edu);

	 // ပုံကို ImageService သုံးပြီး Save လုပ်ပါ
	    if (postDto.getFile() != null && !postDto.getFile().isEmpty()) {
	        String fileName = imageService.save(postDto.getFile(),ImageType.Post); // ဒီနေရာမှာ ပုံကို တကယ် သိမ်းလိုက်တာပါ
	        post.setImage(fileName);
	    }

	    postService.create(post);
	    return "redirect:/post/all";
	}
	// post edit
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {

	    PostModel post = postService.get(id);

	    PostDto dto = new PostDto();
	    dto.setId(post.getId());
	    dto.setName(post.getName());
	    dto.setAddress(post.getAddress());
	    dto.setBornDate(post.getBornDate());
	    dto.setGender(post.getGender());
	    dto.setEducationId(post.getEducation().getId());
	    dto.setImage(post.getImage()); // old image
	    model.addAttribute("post", dto);
	    model.addAttribute("educations", educationService.all());

	    return "post/edit";
	}

	// post update
	@PostMapping("/update/{id}")
	public String update(
	        @PathVariable Long id,
	        @Valid @ModelAttribute("post") PostDto postDto,
	        BindingResult result,
	        Model model) throws IOException {

	    if (result.hasErrors()) {
	        model.addAttribute("educations", educationService.all());
	        return "post/edit";
	    }

	    PostModel post = postService.get(id);

	    post.setName(postDto.getName());
	    post.setAddress(postDto.getAddress());
	    post.setBornDate(postDto.getBornDate());
	    post.setGender(postDto.getGender());

	    // 🔑 education mapping
	    Education edu = educationService.get(postDto.getEducationId());
	    post.setEducation(edu);
	 // ပုံကို ImageService သုံးပြီး Save လုပ်ပါ
	    if (postDto.getFile() != null && !postDto.getFile().isEmpty()) {

	        // 1. save new image
	        String newFileName = imageService.save(postDto.getFile(), ImageType.Post);

	        // 2. delete old image
	        if (post.getImage() != null) {
	            imageService.delete(post.getImage(), ImageType.Post);
	        }

	        // 3. set new image
	        post.setImage(newFileName);

	    } else {
	        post.setImage(postDto.getImage());
	    }

	    postService.edit(id,post);
	    return "redirect:/post/all";
	}
	// post delete
	@GetMapping("/delete/{id}")
	public String deletePost(@PathVariable Long id) {

	    PostModel post = postService.get(id);

	    // 🔥 image 먼저 delete
	    if (post.getImage() != null) {
	        imageService.delete(post.getImage(), ImageType.Post);
	    }

	    // 🔥 post delete
	    postService.drop(id);

	    return "redirect:/post/all";
	}


}
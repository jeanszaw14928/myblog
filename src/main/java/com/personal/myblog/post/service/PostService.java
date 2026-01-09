package com.personal.myblog.post.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.personal.myblog.exception.PostNotFoundException;
import com.personal.myblog.image.ImageService;
import com.personal.myblog.post.Dto.PostDto;
import com.personal.myblog.post.model.PostModel;
import com.personal.myblog.post.repo.PostRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	
	// post repo
	private final PostRepo postRepo;
	
	// post all
	public List<PostModel> all(Model model){
		return postRepo.findAll();
	}
	
	// post create service
	public void create(PostModel post) {		
		postRepo.save(post);
	}
	
	// find by post id
	public PostModel get(Long id) {
		return postRepo.findById(id).orElseThrow(() -> new PostNotFoundException("No post found with that id!"));
	}
	
	// post edit
	public void edit(@PathVariable Long id,PostModel postModel) {
		PostModel postdb = get(id);
		if(postdb != null){
			postdb.setName(postModel.getName());
			postdb.setAddress(postModel.getAddress());
			postdb.setImage(postModel.getImage());
			postdb.setGender(postModel.getGender());
			postdb.setBornDate(postModel.getBornDate());
			postdb.setEducation(postModel.getEducation());
			postRepo.save(postdb);
		}
	}
	
	// born date change age
	 public int calculateAge(LocalDate bornDate) {
	        if (bornDate == null) {
	            return 0;
	        }
	        return Period.between(bornDate, LocalDate.now()).getYears();
	    }

}

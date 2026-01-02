package com.personal.myblog.education.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.personal.myblog.education.model.Education;
import com.personal.myblog.education.repo.EducationRepo;
import com.personal.myblog.exception.EducationNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EducationService {

	// education repository
	private final EducationRepo educationRepo;
	
	// all
	public List<Education>all(){
		return educationRepo.findAll();
	}
	
	// add
	public void add(Education education) {
		educationRepo.save(education);
	}
	
	// find by id
	public Education get(int id) {
		return educationRepo.findById(id).orElseThrow(() -> new EducationNotFoundException("No Education with that id!"));
	}
	
	// update
	public void update(@PathVariable int id, @RequestParam String name) {
		Education education = get(id);
		if(education != null) {
			education.setName(name);
			educationRepo.save(education);
		}
	}
	
	// delete
	public void drop(@PathVariable int id) {
		educationRepo.deleteById(id);
	}
}

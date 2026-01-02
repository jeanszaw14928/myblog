package com.personal.myblog.education.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.personal.myblog.education.model.Education;
import com.personal.myblog.education.service.EducationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("education")
@RequiredArgsConstructor
public class EducationController {

	// education service
	private final EducationService educationService;
	
	// all education
	@GetMapping("/all")
	public String eduAll(Model model) {
		List<Education> education = educationService.all();
		model.addAttribute("education",education);
		return "education/education.html";
	}
	
	// create
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("education", new Education());
		return "education/edu_create.html";
	}
	// add education
	@PostMapping("/create")
	public String eduCreate(@Valid @ModelAttribute("education") Education education,BindingResult result,Model model) {
		if(result.hasErrors()) {
			return "education/edu_create";
		}		
		educationService.add(education);
		return "redirect:/education/all";
	}
	
	// edit
	@GetMapping("edit/{id}")
	public String edit(@PathVariable int id,Model model) {
		Education education = educationService.get(id);
		model.addAttribute("education",education);
		return "education/edu_update.html";
	}
	
	// update
	@PostMapping("edit/{id}")
	public String update(@PathVariable int id,@Valid @ModelAttribute("education") Education education,BindingResult result) {
		if(result.hasErrors()) {
			return "education/edu_update";
		}
		educationService.update(id, education.getName());
		return "redirect:/education/all";
	}
	
	// delete
	@GetMapping("delete/{id}")
	public String delete(@PathVariable int id) {
		educationService.drop(id);
		return "redirect:/education/all";
	}
}

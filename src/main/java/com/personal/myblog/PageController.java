package com.personal.myblog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class PageController {

	@GetMapping("")
	public String dashboard(@RequestParam(name = "view", required=false, defaultValue="post") String view, Model model) {
		model.addAttribute("currentView",view);
		return "dashboard";
	}
	
	
	@GetMapping("edu")
	public String edu() {
		return "education/index";
	}
}

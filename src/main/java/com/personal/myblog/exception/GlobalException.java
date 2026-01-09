package com.personal.myblog.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
	@ExceptionHandler(Exception.class)
	public String handlerException(Exception e, Model model) {
		model.addAttribute("errMsg", e.getMessage());
		return "errorpage";
	}
	
	// education error
	@ExceptionHandler(EducationNotFoundException.class)
	public String handleException(EducationNotFoundException e, Model model) {
		model.addAttribute("errMsg", e.getMessage());
		return "errorpage";
	}
	
	// post error
		@ExceptionHandler(PostNotFoundException.class)
		public String handleException(PostNotFoundException e, Model model) {
			model.addAttribute("errMsg", e.getMessage());
			return "errorpage";
		}

}

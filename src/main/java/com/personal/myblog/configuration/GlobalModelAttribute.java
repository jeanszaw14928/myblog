package com.personal.myblog.configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.personal.myblog.book.service.BookService;

@ControllerAdvice
public class GlobalModelAttribute {
	@Autowired
	private BookService bookService;
	
	@ModelAttribute
	public void addGlobalAttribute(Model model) {
	
		
		Pageable firstFive = PageRequest.of(0, 5);
		
		// image path
		model.addAttribute("bookImagePath", "/images/book-images/");
		model.addAttribute("postImagePath","/images/post-images/");
		
		// main title path
		model.addAttribute("mainTitle","MyBlog");
		
		model.addAttribute("books", bookService.bookAll(null, firstFive));
		
		}
	
		
}
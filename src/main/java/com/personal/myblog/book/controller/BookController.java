package com.personal.myblog.book.controller;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Pageable;
import com.personal.myblog.book.dto.BookDto;
import com.personal.myblog.book.model.Book;
import com.personal.myblog.book.service.BookService;
import com.personal.myblog.image.ImageService;
import com.personal.myblog.image.ImageType;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("book")
public class BookController {

	private final ImageService imageService;
	private final BookService bookService;
	
	// all books
	@GetMapping("/all")
	public String bookAll(
			@RequestParam (defaultValue="")String author,
			@RequestParam(defaultValue="0")int page,
			@RequestParam(defaultValue="5") int size,
			Model model) {
		
		Pageable pageable = PageRequest.of(page, size,Sort.by("author").ascending());
		Page<Book> bookPage = bookService.bookAll(author, pageable);
		model.addAttribute("bookPage",bookPage);
		return "book/index";
	}
	
	// create page
	@GetMapping("/create")
	public String create() {
		return "book/book_create";
	}
	
	// create form
	@PostMapping("/create")
	public String create(@Valid @ModelAttribute BookDto bookDto,BindingResult result) throws IOException {
		if(result.hasErrors()) {
			return"book/book_create";
		}
		
		// create done
		Book book = new Book();
		book.setAuthor(bookDto.getAuthor());
		book.setPublishDate(bookDto.getPublishDate());
		// image
		if(bookDto.getFile() != null && !bookDto.getFile().isEmpty()) {
			String imagePath = imageService.save(bookDto.getFile(), ImageType.Book);
			book.setImage(imagePath);
		}
		
		bookService.create(book);
		return "redirect:/book/all";
	}
	
	// edit
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable int id,Model model) {
		
		Book book = bookService.getId(id);
		
		BookDto bookDto = new BookDto();
		bookDto.setId(book.getId());
		bookDto.setAuthor(book.getAuthor());
		bookDto.setPublishDate(book.getPublishDate());
		bookDto.setImage(book.getImage());
		
		model.addAttribute("book",bookDto);
		return "book/edit-book";
	}
	
	// update 
	@PostMapping("/update/{id}")
	public String update(@Valid @PathVariable int id,@ModelAttribute BookDto bookDto,BindingResult result,Model model) throws IOException {
		
		if(result.hasErrors()) {
			return "book/edit-book";
		}
		
		Book book = bookService.getId(id);
		book.setAuthor(bookDto.getAuthor());
		book.setPublishDate(bookDto.getPublishDate());
		
		// image save new upload
		if(bookDto.getFile() != null && !bookDto.getFile().isEmpty()) {
			String fileName = imageService.save(bookDto.getFile(),ImageType.Book);
			book.setImage(fileName);
		}else {
			// no new upload -keep old image
			book.setImage(bookDto.getImage());
		}
		bookService.update(id, book);
		
		return "redirect:/book/all";
	}
}

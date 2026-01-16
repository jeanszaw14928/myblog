package com.personal.myblog.book.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.personal.myblog.book.model.Book;
import com.personal.myblog.book.repo.BookRepo;
import com.personal.myblog.exception.BookNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

	// book repo
	private final BookRepo bookRepo;
	
	// book list
	public Page<Book> bookAll(String author,Pageable pageable){
		if (author == null || author.isBlank()) {
	        return bookRepo.findAll(pageable);
	    }

		return bookRepo.findByAuthorContaining(author,pageable);
	}
	
	
	
	// find by id
	public Book getId(int id) {
		return bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException("No Book found with that id!"));
	}
	
	// create
	public void create(Book book) {
		bookRepo.save(book);
	}
	
	// update
	public void update(@PathVariable int id,Book book) {
		Book bookDb = getId(id);
		if(bookDb != null) {
			bookDb.setAuthor(book.getAuthor());
			bookDb.setPublishDate(book.getPublishDate());
			bookDb.setImage(book.getImage());
			bookRepo.save(bookDb);
		}
	}
	
	//  delete
	public void drop(int id) {
		bookRepo.deleteById(id);
	}
}
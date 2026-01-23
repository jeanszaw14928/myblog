package com.personal.myblog.book.service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
	@Cacheable(value="books")
	public Page<Book> bookAll(String author,Pageable pageable){
		System.out.println("---fetching from database(Not from Cache)---");
		
		if (author == null || author.isBlank()) {
	        return bookRepo.findAll(pageable);
	    }
		System.out.println("--- Fetching from Database (Not from Cache) ---");
		return bookRepo.findByAuthorContaining(author,pageable);
	}
	
	
	
	// find by id
	public Book getId(int id) {
		return bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException("No Book found with that id!"));
	}
	
	// create
	@CacheEvict(value = "books", allEntries = true)
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
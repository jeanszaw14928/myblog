package com.personal.myblog.book.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.personal.myblog.book.model.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {
	Page<Book> findByAuthor(String author,Pageable pageable);
	
	// search
	Page<Book> findByAuthorContaining(String author,Pageable pageable);
}

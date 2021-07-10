package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
	@Query("SELECT b FROM Book b WHERE b.user.id = :userId ORDER BY b.id DESC")
    List<Book> findByUserId(Long userId);
}

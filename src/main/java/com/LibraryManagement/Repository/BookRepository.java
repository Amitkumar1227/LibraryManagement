package com.LibraryManagement.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LibraryManagement.Entity.Book;
import com.LibraryManagement.Entity.Department;

@Repository
public interface BookRepository extends JpaRepository<Book, String>{
		
	Optional<Book> findByISBN(String ISBN);
}

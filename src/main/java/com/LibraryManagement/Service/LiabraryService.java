package com.LibraryManagement.Service;



import java.util.List;

import org.springframework.stereotype.Component;

import com.LibraryManagement.Entity.Book;
import com.LibraryManagement.Entity.Department;

@Component
public interface LiabraryService {
	
	public Book addBook(String departmentId, Book book);
	public void removeBook(String ISBN);
	public List<Book> findBookByTitle(String title);
	public List<Book> findBookbyAuthor(String author);
	public boolean availability(String ISBN);
	public List<Book> listAllBooks();
	public List<Book> listAvailableBooks();
	public Department addDepartment(Department department);
	public Department getDepartmentById(String departmentId);
	

}

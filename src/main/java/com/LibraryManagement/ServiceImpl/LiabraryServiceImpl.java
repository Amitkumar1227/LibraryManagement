package com.LibraryManagement.ServiceImpl;

import java.util.ArrayList;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.LibraryManagement.Entity.Book;
import com.LibraryManagement.Entity.Department;
import com.LibraryManagement.Repository.BookRepository;
import com.LibraryManagement.Repository.DepartmentRepository;
import com.LibraryManagement.Service.LiabraryService;


@Service
public class LiabraryServiceImpl implements LiabraryService{
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(LiabraryServiceImpl.class);
    
	@Override
	public Book addBook(String departmentId, Book book) {
		logger.info("Starting addBook process for departmentId: {} and book: {}", departmentId, book);
		/* First, we look up the department in which we want to add the book */
		Department department = departmentRepository.findById(departmentId) 
		            .orElseThrow();
		 logger.debug("Department found: {}", department);
		 	/*
			 * We check if the ISBN of the book  is either null or empty. ISBN is a unique identifier for books. If the ISBN is null or
			 * empty, we throw an error saying "ISBN cannot be null or empty".
			 */
		        if (book.getISBN() == null || book.getISBN().isEmpty()) {
		        	  logger.error("ISBN cannot be null or empty");
		            throw new IllegalArgumentException("ISBN cannot be null or empty");
		        }else {
					book.setAvailable(true);
				}
		        logger.debug("Valid ISBN provided: {}", book.getISBN());
				/*
				 * We verify if a book with the same ISBN already exists in the database using bookRepository.findByISBN(book.getISBN()).isPresent(). If a book with the
				 * same ISBN exists (isPresent() returns true), we throw an error saying "Book with this ISBN already exists".
				 */
		        if (bookRepository.findByISBN(book.getISBN()).isPresent()) {
		        	logger.error("Book with ISBN {} already exists", book.getISBN());
		            throw new IllegalArgumentException("Book with this ISBN already exists");
		        }
		        
				/*
				 * If the ISBN is valid and unique, we generate a unique identifier for the book
				 * . We set the department for this book .
				 */
		        logger.debug("ISBN is unique: {}", book.getISBN());
		       book.setBookId(UUID.randomUUID().toString());
		        book.setDepartment(department);
		        
		        book = bookRepository.save(book);
		        logger.info("Book saved successfully: {}", book);
				/*
				 * After saving the book, we add this book to the list of books in the
				 * department . Finally, we save the updated department back to the database.
				 */
		        department.getBooks().add(book); 
		        departmentRepository.save(department);
		        logger.info("Book added to department and department updated successfully");
		        return book;
	}

	@Override //success
	public void removeBook(String ISBN) {
		Book book = bookRepository.findByISBN(ISBN)
				.orElseThrow(() -> new IllegalArgumentException("Book not found with ISBN: " + ISBN));
		bookRepository.delete(book);
		System.out.println("Deleted book with ISBN: " + ISBN);

	}

	@Override //success
	public List<Book> findBookByTitle(String title) {
		List<Book> sameTitledBooks = new ArrayList<>();
		
		for (Book book : bookRepository.findAll()) {
			if (book.getTitle().equalsIgnoreCase(title)) {
				sameTitledBooks.add(book);
			}
		}
		
		return sameTitledBooks;
	}

	@Override //success
	public List<Book> findBookbyAuthor(String author) {
		List<Book> sameAuthorBooks = new ArrayList<>();
		
		for (Book authorBook : bookRepository.findAll()) {
			if (authorBook.getAuthor().equalsIgnoreCase(author)) {
				sameAuthorBooks.add(authorBook);
			}
		}
		return sameAuthorBooks;
	}

	@Override //success
	public List<Book> listAllBooks() {
		List<Book> findAllBooks = bookRepository.findAll();
		return findAllBooks;
	}

	@Override //success
	public List<Book> listAvailableBooks() {
		List<Book> listOfAvailableBooks= new ArrayList<>();
		
		for (Book availableBook : bookRepository.findAll()) {
			if (availableBook.isAvailable()) {
				listOfAvailableBooks.add(availableBook);
			} 
			}
		
		return listOfAvailableBooks;
}

	@Override
	public boolean availability(String ISBN) {
		
		

	    try {
	        Optional<Book> bookOptional = bookRepository.findByISBN(ISBN);
	        if (bookOptional.isPresent()) {
	            return bookOptional.get().isAvailable();
	        } else {
	            throw new IllegalArgumentException("ISBN is not present in database...Please check again");
	        }
	    } catch (IllegalArgumentException e) {
	        logger.error("An error occurred: " + e.getMessage());
	        throw e; 
	    } catch (Exception e) {
	        logger.error("An unexpected error occurred while checking availability: " + e.getMessage(), e);
	        return false; 
	    }
		//return bookRepository.findById(ISBN).map(Book::isAvailable).orElse(false);
	}

	@Override //success
	public Department addDepartment(Department department) {
		department.setDepartmentId(UUID.randomUUID().toString());
		Department savedDepartment = departmentRepository.save(department);
		return savedDepartment;
	}

	@Override //success
	public Department getDepartmentById(String departmentId) {
		Optional<Department> findByIdDepartment = departmentRepository.findById(departmentId);
		return findByIdDepartment.orElse(null);
	}
	

}
package com.LibraryManagement.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.LibraryManagement.Entity.Book;
import com.LibraryManagement.Entity.Department;
import com.LibraryManagement.Repository.BookRepository;
import com.LibraryManagement.Service.LiabraryService;

@RestController
@RequestMapping("/books")

public  class LibraryController {
    
	@Autowired
	private LiabraryService liabraryService;
	
	private static final Logger logger= LoggerFactory.getLogger(LibraryController.class);
	
	@PostMapping("/addBook") //success
    public ResponseEntity<?> addBook(@RequestParam String departmentId, @RequestBody Book book) {
       
		      try {
        	
        	 Book addBook = liabraryService.addBook(departmentId,book);
             return new ResponseEntity<Book>(addBook, HttpStatus.CREATED); 
			
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
		}
         
    }

    @DeleteMapping("/removeBook/{isbn}")
    public ResponseEntity<String> removeBook(@PathVariable String isbn) {
        liabraryService.removeBook(isbn);
        return new ResponseEntity<String>(isbn + " has been deleted", HttpStatus.OK);
    }

    @GetMapping("/title/{title}") 
    public ResponseEntity<List<Book>> findBookByTitle(@PathVariable String title) {
         List<Book> findBookByTitle = liabraryService.findBookByTitle(title);
         return new ResponseEntity<List<Book>>(findBookByTitle, HttpStatus.FOUND);
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> findBookByAuthor(@PathVariable String author) {
         List<Book> findBookbyAuthor = liabraryService.findBookbyAuthor(author);
         return new ResponseEntity<List<Book>>(findBookbyAuthor, HttpStatus.CREATED);
    }

    @GetMapping("/allbooks")
    public ResponseEntity<List<Book>> listAllBooks() {
         List<Book> listAllBooks = liabraryService.listAllBooks();
         return new ResponseEntity<List<Book>>(listAllBooks, HttpStatus.FOUND);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Book>> listAvailableBooks() { 
         List<Book> listAvailableBooks = liabraryService.listAvailableBooks();
         return new ResponseEntity<List<Book>>(listAvailableBooks, HttpStatus.FOUND);
    }
    
    @GetMapping("/checkAvailability/{BookISBN}")
    public ResponseEntity<?> isBookAvailable(@PathVariable("BookISBN") String isbn){
    	
    	try {
            boolean isAvailable = liabraryService.availability(isbn);
            return new ResponseEntity<>(isAvailable, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Log the specific IllegalArgumentException and return a bad request response
            logger.error("An error occurred: " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (Exception e) { 
            // Log any other unexpected exceptions and return an internal server error response
            logger.error("An unexpected error occurred while checking availability: " + e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    	
    	
    	}
    
    @PostMapping("/addDepartment") 
    public ResponseEntity<Department> addDepatments(@RequestBody Department department){
    	Department addDepartment = liabraryService.addDepartment(department);
    	return new ResponseEntity<Department>(addDepartment, HttpStatus.CREATED);
    }
    @GetMapping("/{departmentId}")
    public ResponseEntity<Department> getDepatmentsById(@PathVariable("departmentId")  String id){
    	Department departmentById = liabraryService.getDepartmentById(id);
    	if (departmentById != null) {
			return ResponseEntity.ok(departmentById);
		}else {
			
			return ResponseEntity.notFound().build();
		}
    	
    }
    
    
	
}

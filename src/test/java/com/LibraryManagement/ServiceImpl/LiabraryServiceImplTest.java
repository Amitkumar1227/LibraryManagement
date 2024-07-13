package com.LibraryManagement.ServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.LibraryManagement.Entity.Book;
import com.LibraryManagement.Entity.Department;
import com.LibraryManagement.Repository.BookRepository;
import com.LibraryManagement.Repository.DepartmentRepository;

class LiabraryServiceImplTest {
	@Mock
    private BookRepository bookRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private LiabraryServiceImpl libraryServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

	@Test
	void testAddBook() { 
		String departmentId = UUID.randomUUID().toString();
	    Department department = new Department();
	    department.setDepartmentId(departmentId);
	    department.setDepartmentName("Engineering");

	    Book book = new Book();
	    book.setISBN("1234567890");
	    book.setTitle("Test Book");
	    book.setAuthor("Test Author");
	    book.setGenre("Test Genre");
	    book.setPublicationYear(2021);

	    when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
	    when(bookRepository.findByISBN(book.getISBN())).thenReturn(Optional.empty());
	    when(bookRepository.save(any(Book.class))).thenReturn(book);
	    when(departmentRepository.save(any(Department.class))).thenReturn(department);

	    Book result = libraryServiceImpl.addBook(departmentId, book);

	    assertNotNull(result);
	    assertEquals("1234567890", result.getISBN());
	    verify(departmentRepository, times(1)).findById(departmentId);
	    verify(bookRepository, times(1)).findByISBN(book.getISBN());
	    verify(bookRepository, times(1)).save(book);
	    verify(departmentRepository, times(1)).save(department);
	}

	@Test
	void testRemoveBook() {
		String ISBN = "1234567890";
	    Book book = new Book();
	    book.setISBN(ISBN);

	    when(bookRepository.findByISBN(ISBN)).thenReturn(Optional.of(book));

	    libraryServiceImpl.removeBook(ISBN);

	    verify(bookRepository, times(1)).findByISBN(ISBN);
	    verify(bookRepository, times(1)).delete(book);
	}

	@Test
	void testFindBookByTitle() {
		String title = "Test Book";
	    List<Book> books = new ArrayList<>();
	    Book book = new Book();
	    book.setTitle(title);
	    books.add(book);

	    when(bookRepository.findAll()).thenReturn(books);

	    List<Book> result = libraryServiceImpl.findBookByTitle(title);

	    assertNotNull(result);
	    assertEquals(1, result.size());
	    assertEquals(title, result.get(0).getTitle());
	    verify(bookRepository, times(1)).findAll();
	}

	@Test
	void testFindBookbyAuthor() {
		 String author = "Test Author";
		    List<Book> books = new ArrayList<>();
		    Book book = new Book();
		    book.setAuthor(author);
		    books.add(book);

		    when(bookRepository.findAll()).thenReturn(books);

		    List<Book> result = libraryServiceImpl.findBookbyAuthor(author);

		    assertNotNull(result);
		    assertEquals(1, result.size());
		    assertEquals(author, result.get(0).getAuthor());
		    verify(bookRepository, times(1)).findAll();
	}

	@Test
	void testListAllBooks() {
	    List<Book> books = new ArrayList<>();
	    Book book = new Book();
	    books.add(book);

	    when(bookRepository.findAll()).thenReturn(books);

	    List<Book> result = libraryServiceImpl.listAllBooks();

	    assertNotNull(result);
	    assertEquals(1, result.size());
	    verify(bookRepository, times(1)).findAll();
	}

	@Test
	void testListAvailableBooks() {
		 List<Book> books = new ArrayList<>();
		    Book book = new Book();
		    book.setAvailable(true);
		    books.add(book);

		    when(bookRepository.findAll()).thenReturn(books);

		    List<Book> result = libraryServiceImpl.listAvailableBooks();

		    assertNotNull(result);
		    assertEquals(1, result.size());
		    assertTrue(result.get(0).isAvailable());
		    verify(bookRepository, times(1)).findAll();
	}

	@Test
	void testAvailability() {
		 String ISBN = "1234567890";
		    Book book = new Book();
		    book.setISBN(ISBN);
		    book.setAvailable(true);

		    when(bookRepository.findByISBN(ISBN)).thenReturn(Optional.of(book));

		    boolean result = libraryServiceImpl.availability(ISBN);

		    assertTrue(result);
		    verify(bookRepository, times(1)).findByISBN(ISBN);
	}

	@Test
	void testAddDepartment() {
		 Department department = new Department();
		    department.setDepartmentName("Engineering");

		    when(departmentRepository.save(any(Department.class))).thenReturn(department);

		    Department result = libraryServiceImpl.addDepartment(department);

		    assertNotNull(result);
		    assertEquals("Engineering", result.getDepartmentName());
		    verify(departmentRepository, times(1)).save(department);
	}

	@Test
	void testGetDepartmentById() {
		String departmentId = UUID.randomUUID().toString();
	    Department department = new Department();
	    department.setDepartmentId(departmentId);
	    department.setDepartmentName("Engineering");

	    when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

	    Department result = libraryServiceImpl.getDepartmentById(departmentId);

	    assertNotNull(result);
	    assertEquals(departmentId, result.getDepartmentId());
	    verify(departmentRepository, times(1)).findById(departmentId);
	}

}

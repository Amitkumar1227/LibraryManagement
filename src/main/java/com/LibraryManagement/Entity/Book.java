package com.LibraryManagement.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Books")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "department")
public class Book {
	
    @Id
	private String bookId;
	@Column(name = "BookName")
	private String title;  // Title of the book
	@Column(name = "Author")
    private String author; // Author of the book
	
	@Column(name = "ISBN", nullable = false)
  	private String ISBN;  // ISBN number of the book unique fo each publication ISBN 3747684938
	
	@Column(name = "GenreOfBook")
    private String genre; 
	@Column(name = "PubYear")// Genre of the book, romance, fiction, story ....
    private int publicationYear;  // Publication year of the book
	
    @ManyToOne
    @JoinColumn(name ="departmentId")
    @JsonBackReference
	private Department department;   
	@Column(name = "Availability")// Department of the book
    private boolean isAvailable;  // Availability of books

	/*@JsonBackReference is used on the child side of the relationship (in Book), and it will prevent the serialization of the back reference to the department, breaking the cycle.
*/
}
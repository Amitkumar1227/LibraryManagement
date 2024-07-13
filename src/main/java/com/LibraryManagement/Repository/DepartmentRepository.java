package com.LibraryManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.LibraryManagement.Entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String>{
	



}

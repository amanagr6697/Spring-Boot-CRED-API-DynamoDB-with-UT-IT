package com.ninja.RestAPISpringBootWithDynamoDB.service;

import java.util.Optional;

import com.ninja.RestAPISpringBootWithDynamoDB.entity.Student;
import com.ninja.RestAPISpringBootWithDynamoDB.repository.StudentRepository;
import org.springframework.stereotype.Service;

import com.ninja.RestAPISpringBootWithDynamoDB.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class StudentService {

	@Autowired
	StudentRepository studentRepo;

	public StudentService(StudentRepository studentRepo) {
    this.studentRepo = studentRepo;
  }
	public Iterable<Student> getStudents() {
		return studentRepo.findAll();
	}

	public Optional<Student> getStudent(String id) {
		boolean exists=studentRepo.existsById(id);
		if (exists) {
			return studentRepo.findById(id);
		}
		else
			throw new EntityNotFoundException("Course not found with the given ID");
	}

	public Student updateStudent(Student student, String id) {
		boolean exists = studentRepo.existsById(id);
		if(!exists) {
			throw new EntityNotFoundException("Student(id- " + id + ") Not Found !!");
		}
		else {
			student.setId(id);
			studentRepo.save(student);
		}
			return student;
	}

	public void deleteStudent(String id) {
		boolean exists = studentRepo.existsById(id);
		if(!exists) {
			throw new EntityNotFoundException("Student(id- " + id + ") Not Found !!");
		}
		else
			studentRepo.deleteById(id);	
	}
	
	public Student addStudent(Student student) {		
		studentRepo.save(student);
		return student;
	}

}

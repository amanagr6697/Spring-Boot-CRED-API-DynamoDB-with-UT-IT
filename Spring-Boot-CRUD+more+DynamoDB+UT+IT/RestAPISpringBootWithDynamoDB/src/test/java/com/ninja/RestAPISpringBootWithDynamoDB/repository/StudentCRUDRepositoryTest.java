package com.ninja.RestAPISpringBootWithDynamoDB.repository;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ninja.RestAPISpringBootWithDynamoDB.entity.Student;
import com.ninja.RestAPISpringBootWithDynamoDB.repository.StudentCRUDRepository;
import com.ninja.RestAPISpringBootWithDynamoDB.repository.StudentRepository;
import com.ninja.RestAPISpringBootWithDynamoDB.entity.Student;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudentCRUDRepositoryTest {

  @Autowired
  StudentRepository StudentRepository;

  @BeforeEach
  void setUp() {
    Student student=new Student();
    student.setFirstName("Amit");
    student.setLastName("Agrawal");
    student.setEmail("amit@iitp.ac.in");
    student.setCourseEnrolled("Spring");
    student.setId("7");
    StudentRepository.save(student);
  }

  @Test
  void save() {
    assertNotNull(StudentRepository.findById("7"));
  }

  @Test
  void findById() {
    Student student=StudentRepository.findById("7").get();
    assertThat(student.getCourseEnrolled()).isEqualTo("Spring");
  }

  @Test
  void findAll() {
    List<Student> Students=StudentRepository.findAll();
    Integer size=Students.size();
    assertThat(size).isGreaterThan(0);
  }

  @Test
  void existsById() {
    boolean exists=StudentRepository.existsById("7");
    assertThat(exists).isTrue();
  }

  @Test
  void findByStudentId() {
    Optional<Student> Student=StudentRepository.findById("7");
    assertThat(Student.isPresent()).isTrue();
    assertThat(Student.get().getCourseEnrolled()).isEqualTo("Spring");
    assertThat(Student.get().getFirstName()).isEqualTo("Amit");
  }

  @Test
  void deleteById() {
    StudentRepository.deleteById("7");
    assertThat(StudentRepository.existsById("7")).isFalse();
  }

  @AfterEach
  void tearDown() {
    System.out.println("Deleting");
  }
}
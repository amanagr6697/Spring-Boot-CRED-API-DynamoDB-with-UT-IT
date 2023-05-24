package com.ninja.RestAPISpringBootWithDynamoDB.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ninja.RestAPISpringBootWithDynamoDB.entity.Student;
import com.ninja.RestAPISpringBootWithDynamoDB.repository.StudentRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
  @Mock
  private StudentRepository studentRepo;
  @InjectMocks
  private StudentService studentService;

  @BeforeEach
  void setUp() {
    studentService = new StudentService(studentRepo);
  }

  @Test
  void getStudentsAll() {
    studentService.getStudents();
    verify(studentRepo).findAll();
  }

  @Test
  void getStudentTest() {
    Student mockStudent = new Student();
    mockStudent.setCourseEnrolled("Spring");
    mockStudent.setFirstName("Aman");
    mockStudent.setLastName("Agrawal");
    mockStudent.setEmail("aman_2001cs04@iitp.ac.in");
    mockStudent.setId("1");

    String studentId="1";

    when(studentRepo.existsById(mockStudent.getId())).thenReturn(true);
    when(studentRepo.findById(mockStudent.getId())).thenReturn(Optional.of(mockStudent));
    Optional<Student> result=studentService.getStudent(studentId);
    assertTrue(result.isPresent());
    assertThat(mockStudent).isEqualTo(result.get());
  }

  @Test
  void updateStudentTest() {
    Student mockStudent = new Student();
    mockStudent.setCourseEnrolled("Spring");
    mockStudent.setFirstName("Aman");
    mockStudent.setLastName("Agrawal");
    mockStudent.setEmail("aman_2001cs04@iitp.ac.in");

    mockStudent.setId("1");

    String studentId="1";
    when(studentRepo.existsById(studentId)).thenReturn(true);
    doAnswer(invocation -> {
      Student savedStudent = invocation.getArgument(0);
      assertEquals(mockStudent, savedStudent);
      assertEquals(studentId, savedStudent.getId());
      return null;
    }).when(studentRepo).save(mockStudent);

    Student result = studentService.updateStudent(mockStudent, studentId);

    assertEquals(mockStudent, result);
    assertEquals(studentId, result.getId());
  }

  @Test
  void deleteStudentTest() {
    Student mockStudent = new Student();
    mockStudent.setCourseEnrolled("Spring");
    mockStudent.setFirstName("Aman");
    mockStudent.setLastName("Agrawal");
    mockStudent.setEmail("aman_2001cs04@iitp.ac.in");

    mockStudent.setId("1");

    String studentId="1";

    when(studentRepo.existsById(studentId)).thenReturn(true);

    doAnswer(invocation -> {
      String deletedStudentId = invocation.getArgument(0);
      assertEquals(studentId, deletedStudentId);
      return null;
    }).when(studentRepo).deleteById( studentId);

    studentService.deleteStudent(studentId);
    verify(studentRepo,times(1)).deleteById(studentId);
  }

  @Test
  void addStudent() {
    Student student=new Student();
    student.setCourseEnrolled("Spring");
    student.setFirstName("Aman");
    student.setLastName("Agrawal");
    student.setEmail("aman_2001cs04@iitp.ac.in");
    student.setId("1");
    studentService.addStudent(student);
    ArgumentCaptor<Student> toCapturePassedStudent=ArgumentCaptor.forClass(Student.class);
    verify(studentRepo).save(toCapturePassedStudent.capture());
    assertThat(toCapturePassedStudent.getValue()).isEqualTo(student);
  }
}
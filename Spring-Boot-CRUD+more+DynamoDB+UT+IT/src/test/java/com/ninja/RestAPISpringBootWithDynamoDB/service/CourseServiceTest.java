package com.ninja.RestAPISpringBootWithDynamoDB.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ninja.RestAPISpringBootWithDynamoDB.entity.Course;
import com.ninja.RestAPISpringBootWithDynamoDB.entity.Student;
import com.ninja.RestAPISpringBootWithDynamoDB.repository.CourseRepository;
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
class CourseServiceTest {
  @Mock
  private CourseRepository courseRepo;
  @Mock
  private StudentRepository studentRepo;
  @InjectMocks
  private CourseService courseService;

  @BeforeEach
  void setUp() {
    courseService = new CourseService(courseRepo,studentRepo);
  }

  @Test
  void getCoursesAll() {
    courseService.getCourses();
    verify(courseRepo).findAll();
  }

  @Test
  void getCourse1() {
    Course mockCourse = new Course();
    mockCourse.setCourseName("Spring");
    mockCourse.setTaughtByProf("Aman");
    mockCourse.setId("1");

    String courseId="1";

    when(courseRepo.existsById(mockCourse.getId())).thenReturn(true);
    when(courseRepo.findById(mockCourse.getId())).thenReturn(Optional.of(mockCourse));
    Optional<Course> result=courseService.getCourse(courseId);
    assertTrue(result.isPresent());
    assertThat(mockCourse).isEqualTo(result.get());
  }

  @Test
  void updateCourseTest() {
    Course mockCourse = new Course();
    mockCourse.setCourseName("Spring");
    mockCourse.setTaughtByProf("Aman");
    mockCourse.setId("1");

    String courseId="1";
    when(courseRepo.existsById(courseId)).thenReturn(true);
    doAnswer(invocation -> {
      Course savedCourse = invocation.getArgument(0);
      assertEquals(mockCourse, savedCourse);
      assertEquals(courseId, savedCourse.getId());
      return null;
    }).when(courseRepo).save(mockCourse);
    
    Course result = courseService.updateCourse(mockCourse, courseId);

    assertEquals(mockCourse, result);
    assertEquals(courseId, result.getId());
  }

  @Test
  void deleteCourseTest() {
    Course mockCourse = new Course();
    mockCourse.setCourseName("Spring");
    mockCourse.setTaughtByProf("Aman");
    mockCourse.setId("1");

    String courseId="1";

    when(courseRepo.existsById(courseId)).thenReturn(true);

    doAnswer(invocation -> {
      String deletedCourseId = invocation.getArgument(0);
      assertEquals(courseId, deletedCourseId);
      return null;
    }).when(courseRepo).deleteById( courseId);

    courseService.deleteCourse(courseId);
    verify(courseRepo,times(1)).deleteById(courseId);
  }

  @Test
  void addCourse() {
    Course course=new Course();
    course.setCourseName("Spring");
    course.setTaughtByProf("Aman");
    course.setId("7");
    courseService.addCourse(course);
    ArgumentCaptor<Course> toCapturePassedCourse=ArgumentCaptor.forClass(Course.class);
    verify(courseRepo).save(toCapturePassedCourse.capture());
    assertThat(toCapturePassedCourse.getValue()).isEqualTo(course);
  }

  @Test
  void queryEntityAWithEntityBTest() {
    Student mockStudent = new Student();
    mockStudent.setFirstName("Aman");
    mockStudent.setLastName("Rocks");
    mockStudent.setEmail("agrawalaman4321@gmail.com");
    mockStudent.setId("1");
    mockStudent.setCourseEnrolled("Promotion-Service");

    String courseName="Promotion-Service";

    when(studentRepo.findByCourseEnrolled(courseName)).thenReturn(List.of(mockStudent));
    List<Student> result=courseService.queryEntityAWithEntityB(courseName);
    assertThat(mockStudent).isEqualTo(result.get(0));
  }
}
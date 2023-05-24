package com.ninja.RestAPISpringBootWithDynamoDB.repository;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.ninja.RestAPISpringBootWithDynamoDB.entity.Course;
import com.ninja.RestAPISpringBootWithDynamoDB.repository.CourseCRUDRepository;
import com.ninja.RestAPISpringBootWithDynamoDB.repository.CourseRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CourseCRUDRepositoryTest {

  @Autowired
  CourseRepository courseRepository;

  @BeforeEach
  void setUp() {
    Course course=new Course();
    course.setCourseName("Spring");
    course.setTaughtByProf("Aman");
    course.setId("7");
    courseRepository.save(course);
  }

  @Test
  void save() {
    assertNotNull(courseRepository.findById("7"));
  }

  @Test
  void findById() {
    Course course=courseRepository.findById("3").get();
    assertThat(course.getCourseName()).isEqualTo("C++");
  }

  @Test
  void findAll() {
    List<Course> courses=courseRepository.findAll();
    Integer size=courses.size();
    assertThat(size).isGreaterThan(0);
  }

  @Test
  void existsById() {
    boolean exists=courseRepository.existsById("7");
    assertThat(exists).isTrue();
  }

  @Test
  void findByCourseId() {
    Optional<Course> course=courseRepository.findById("7");
    assertThat(course.isPresent()).isTrue();
    assertThat(course.get().getCourseName()).isEqualTo("Spring");
    assertThat(course.get().getTaughtByProf()).isEqualTo("Aman");
  }

  @Test
  void deleteById() {
    courseRepository.deleteById("7");
    assertThat(courseRepository.existsById("7")).isFalse();
  }

  @AfterEach
  void tearDown() {
    System.out.println("Deleting");
  }
}
package com.ninja.RestAPISpringBootWithDynamoDB.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninja.RestAPISpringBootWithDynamoDB.entity.Course;
import com.ninja.RestAPISpringBootWithDynamoDB.entity.Student;
import com.ninja.RestAPISpringBootWithDynamoDB.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CourseController.class)
public class CourseControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private CourseService courseService;

  private Course course;
  private Student student;

  @BeforeEach
  public void setUp() {
    course = new Course("1", "Java", "Uma");
    student = new Student("1", "Lorem", "Ipsum", "lorem.ipsum@gmail.com", "Java");

    when(courseService.getCourses()).thenReturn(Arrays.asList(course));
    when(courseService.getCourse("1")).thenReturn(Optional.of(course));
    when(courseService.queryEntityAWithEntityB("Java")).thenReturn(Arrays.asList(student));
    when(courseService.addCourse(course)).thenReturn(course);
    when(courseService.updateCourse(course, "1")).thenReturn(course);
  }

  @Test
  public void getCoursesTest() throws Exception {
    mockMvc.perform(get("/courses"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(course.getId()))
        .andExpect(jsonPath("$[0].courseName").value(course.getCourseName()))
        .andExpect(jsonPath("$[0].taughtByProf").value(course.getTaughtByProf()));
  }

  @Test
  public void getCourseTest() throws Exception {
    mockMvc.perform(get("/course/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(course.getId()))
        .andExpect(jsonPath("$.courseName").value(course.getCourseName()))
        .andExpect(jsonPath("$.taughtByProf").value(course.getTaughtByProf()));
  }

  @Test
  public void getEntityAWithEntityBTest() throws Exception {
    mockMvc.perform(get("/students/Java"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(student.getId()))
        .andExpect(jsonPath("$[0].firstName").value(student.getFirstName()))
        .andExpect(jsonPath("$[0].lastName").value(student.getLastName()))
        .andExpect(jsonPath("$[0].email").value(student.getEmail()))
        .andExpect(jsonPath("$[0].courseEnrolled").value(student.getCourseEnrolled()));
  }

  @Test
  public void addCourseTest() throws Exception {
    mockMvc.perform(post("/course")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(course)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(course.getId()))
        .andExpect(jsonPath("$.courseName").value(course.getCourseName()))
        .andExpect(jsonPath("$.taughtByProf").value(course.getTaughtByProf()));
  }

  @Test
  public void updateCourseTest() throws Exception {
    mockMvc.perform(put("/course/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(course)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(course.getId()))
        .andExpect(jsonPath("$.courseName").value(course.getCourseName()))
        .andExpect(jsonPath("$.taughtByProf").value(course.getTaughtByProf()));
  }

  @Test
  public void deleteCourseTest() throws Exception {
    mockMvc.perform(delete("/course/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.Course_id").value("1"))
        .andExpect(jsonPath("$.message_response").value("The record has been deleted !!"));
  }
}
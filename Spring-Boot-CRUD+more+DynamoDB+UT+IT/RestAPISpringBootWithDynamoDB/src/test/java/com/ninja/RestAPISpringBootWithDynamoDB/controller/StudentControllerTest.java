package com.ninja.RestAPISpringBootWithDynamoDB.controller;

    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.ninja.RestAPISpringBootWithDynamoDB.entity.Student;
    import com.ninja.RestAPISpringBootWithDynamoDB.entity.Student;
    import com.ninja.RestAPISpringBootWithDynamoDB.service.StudentService;
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
    import static org.mockito.ArgumentMatchers.any;
    import static org.mockito.Mockito.times;
    import static org.mockito.Mockito.when;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private StudentService studentService;

  private Student student;

  @BeforeEach
  public void setUp() {
    student = new Student("1", "Lorem", "Ipsum", "lorem.ipsum@gmail.com", "Java");

    when(studentService.getStudents()).thenReturn(Arrays.asList(student));
    when(studentService.getStudent("1")).thenReturn(Optional.of(student));
    when(studentService.addStudent(student)).thenReturn(student);
    when(studentService.updateStudent(student, "1")).thenReturn(student);
  }

  @Test
  public void getStudentsTest() throws Exception {
    mockMvc.perform(get("/students"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(student.getId()))
        .andExpect(jsonPath("$[0].firstName").value(student.getFirstName()))
        .andExpect(jsonPath("$[0].lastName").value(student.getLastName()))
        .andExpect(jsonPath("$[0].email").value(student.getEmail()))
        .andExpect(jsonPath("$[0].courseEnrolled").value(student.getCourseEnrolled()));
  }

  @Test
  public void getStudentTest() throws Exception {
    mockMvc.perform(get("/student/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(student.getId()))
        .andExpect(jsonPath("$.firstName").value(student.getFirstName()))
        .andExpect(jsonPath("$.lastName").value(student.getLastName()))
        .andExpect(jsonPath("$.email").value(student.getEmail()))
        .andExpect(jsonPath("$.courseEnrolled").value(student.getCourseEnrolled()));
  }
  

  @Test
  public void addStudentTest() throws Exception {
    mockMvc.perform(post("/student")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(student)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(student.getId()))
        .andExpect(jsonPath("$.firstName").value(student.getFirstName()))
        .andExpect(jsonPath("$.lastName").value(student.getLastName()))
        .andExpect(jsonPath("$.email").value(student.getEmail()))
        .andExpect(jsonPath("$.courseEnrolled").value(student.getCourseEnrolled()));
  }

  @Test
  public void updateStudentTest() throws Exception {
    mockMvc.perform(put("/student/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(student)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(student.getId()))
        .andExpect(jsonPath("$.firstName").value(student.getFirstName()))
        .andExpect(jsonPath("$.lastName").value(student.getLastName()))
        .andExpect(jsonPath("$.email").value(student.getEmail()))
        .andExpect(jsonPath("$.courseEnrolled").value(student.getCourseEnrolled()));
  }

  @Test
  public void deleteStudentTest() throws Exception {
    mockMvc.perform(delete("/student/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.Student_id").value("1"))
        .andExpect(jsonPath("$.message_response").value("The record has been deleted !!"));
  }
}
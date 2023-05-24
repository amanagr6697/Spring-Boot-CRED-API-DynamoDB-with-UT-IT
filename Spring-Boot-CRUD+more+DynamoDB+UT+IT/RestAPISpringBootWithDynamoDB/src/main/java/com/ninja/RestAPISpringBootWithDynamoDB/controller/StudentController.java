package com.ninja.RestAPISpringBootWithDynamoDB.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.ninja.RestAPISpringBootWithDynamoDB.entity.Student;
import com.ninja.RestAPISpringBootWithDynamoDB.service.StudentService;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@RestController
public class StudentController {

	@Autowired
	StudentService studentService;

  private ObjectMapper objectMapper=new ObjectMapper();
	
	@GetMapping("/students")
    public ResponseEntity<Iterable<Student>> getStudents() {
        return ResponseEntity.ok(studentService.getStudents());
    }
	
	@GetMapping("/student/{id}")
    public ResponseEntity<Optional<Student>> getStudent(@PathVariable String id) {
        return ResponseEntity.ok(studentService.getStudent(id));
    }
	
	@PostMapping("/student")
    public ResponseEntity<Student> addStudent(@RequestBody Student student ) {
        return  ResponseEntity.ok(studentService.addStudent(student));
    }
	
	@PutMapping("/student/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student, @PathVariable String id) {
        return  ResponseEntity.ok(studentService.updateStudent(student,id));
    }
	
	@DeleteMapping("/student/{id}")
  public ResponseEntity<JsonNode> deleteStudent(@PathVariable String id) {
    Map<String, String> responseMap = new LinkedHashMap<>();
    studentService.deleteStudent(id);
    String msg = "The record has been deleted !!";
    responseMap.put("Student_id", id);
    responseMap.put("message_response", msg);

    JsonNode jsonResponse = objectMapper.valueToTree(responseMap);
    return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
  }

}


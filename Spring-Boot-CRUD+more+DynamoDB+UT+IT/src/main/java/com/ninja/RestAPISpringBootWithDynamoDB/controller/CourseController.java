package com.ninja.RestAPISpringBootWithDynamoDB.controller;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import com.ninja.RestAPISpringBootWithDynamoDB.entity.Student;
import com.sun.source.tree.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ninja.RestAPISpringBootWithDynamoDB.service.CourseService;
import com.ninja.RestAPISpringBootWithDynamoDB.entity.Course;

@RestController
public class CourseController {

    @Autowired
    CourseService courseService;
    private ObjectMapper objectMapper = new ObjectMapper();


  @GetMapping("/courses")
    public ResponseEntity<Iterable<Course>> getCourses() {
        return ResponseEntity.ok(courseService.getCourses());
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<Optional<Course>> getCourse(@PathVariable String id) {
        return ResponseEntity.ok(courseService.getCourse(id));
    }
   @GetMapping("/students/{cName}")
    public ResponseEntity<List<Student>> getEntityAWithEntityB(@PathVariable("cName") String courseEnrolled) {
        return ResponseEntity.ok(courseService.queryEntityAWithEntityB(courseEnrolled));
    }

    @PostMapping("/course")
    public ResponseEntity<Course> addCourse(@RequestBody Course Course ) {
        return  ResponseEntity.ok(courseService.addCourse(Course));
    }

    @PutMapping("/course/{id}")
    public ResponseEntity<Course> updateCourse(@RequestBody Course Course, @PathVariable String id) {
        return  ResponseEntity.ok(courseService.updateCourse(Course,id));
    }

  @DeleteMapping("/course/{id}")
  public ResponseEntity<JsonNode> deleteCourse(@PathVariable String id) {
    Map<String, String> responseMap = new LinkedHashMap<>();
    courseService.deleteCourse(id);
    String msg = "The record has been deleted !!";
    responseMap.put("Course_id", id);
    responseMap.put("message_response", msg);

    JsonNode jsonResponse = objectMapper.valueToTree(responseMap);
    return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
  }
}


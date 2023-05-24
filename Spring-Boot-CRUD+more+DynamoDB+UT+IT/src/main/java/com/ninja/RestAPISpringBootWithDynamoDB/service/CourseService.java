package com.ninja.RestAPISpringBootWithDynamoDB.service;

import java.util.List;
import java.util.Optional;

import com.ninja.RestAPISpringBootWithDynamoDB.entity.Student;
import com.ninja.RestAPISpringBootWithDynamoDB.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ninja.RestAPISpringBootWithDynamoDB.exception.EntityNotFoundException;
import com.ninja.RestAPISpringBootWithDynamoDB.repository.CourseRepository;
import com.ninja.RestAPISpringBootWithDynamoDB.entity.Course;

@Service
public class CourseService {

    @Autowired
    private CourseRepository CourseRepo;
    @Autowired
    private StudentRepository studentRepo;

    CourseService(CourseRepository CourseRepo,StudentRepository studentRepo)
    {
        this.studentRepo=studentRepo;
        this.CourseRepo=CourseRepo;
    }
    public List<Course> getCourses() {
        return CourseRepo.findAll();
    }

    public Optional<Course> getCourse(String id) {
        boolean exists=CourseRepo.existsById(id);
        if (exists) {
            return CourseRepo.findById(id);
        }
        else
            throw new EntityNotFoundException("Course not found with the given ID");
    }

    public Course updateCourse(Course Course, String id) {
        boolean exists = CourseRepo.existsById(id);
        if(!exists) {
            throw new EntityNotFoundException("Course(id- " + id + ") Not Found !!");
        }
        else
        {
            Course.setId(id);
            CourseRepo.save(Course);
        }
        return Course;
    }

    public void deleteCourse(String id) {
        boolean exists = CourseRepo.existsById(id);
        if(!exists) {
            throw new EntityNotFoundException("Course(id- " + id + ") Not Found !!");
        }
        else
            CourseRepo.deleteById(id);
    }

    public Course addCourse(Course course) {
        CourseRepo.save(course);
        return course;
    }

    public List<Student> queryEntityAWithEntityB(String courseEnrolled) {
        List<Student> ans=studentRepo.findByCourseEnrolled(courseEnrolled);
        if(ans.isEmpty())
        {
            throw new EntityNotFoundException("No Student found with this course");
        }
        else
           return ans;
    }

}

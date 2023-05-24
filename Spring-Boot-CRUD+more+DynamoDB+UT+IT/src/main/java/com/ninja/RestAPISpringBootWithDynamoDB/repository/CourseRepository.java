package com.ninja.RestAPISpringBootWithDynamoDB.repository;
import com.ninja.RestAPISpringBootWithDynamoDB.entity.Course;
import java.util.Optional;
import java.util.List;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.stereotype.Repository;

@EnableScan
@Repository
public interface CourseRepository{
  void save(Course course);

  Optional<Course> findById(String id);

  List<Course> findAll();

  boolean existsById(String id);

  void deleteById(String id);
}


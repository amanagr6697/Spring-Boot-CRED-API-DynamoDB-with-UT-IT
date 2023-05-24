package com.ninja.RestAPISpringBootWithDynamoDB.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.ninja.RestAPISpringBootWithDynamoDB.entity.Course;
import com.ninja.RestAPISpringBootWithDynamoDB.repository.CourseRepository;
import java.util.List;
import java.util.Optional;

public class CourseCRUDRepository implements CourseRepository
{
  private final DynamoDBMapper dynamoDBMapper;

  public CourseCRUDRepository(DynamoDBMapper dynamoDBMapper) {
    this.dynamoDBMapper = dynamoDBMapper;
  }
  public void save(Course course) {
    dynamoDBMapper.save(course);
  }

  public Optional<Course> findById(String id) {
    return Optional.ofNullable(dynamoDBMapper.load(Course.class, id));
  }

  public List<Course> findAll()
  {
    DynamoDBScanExpression dynamoDBScanExpression=new DynamoDBScanExpression();
    PaginatedScanList<Course> result = dynamoDBMapper.scan(Course.class,dynamoDBScanExpression);
    return result.subList(0, result.size());
  }

  public boolean existsById(String id)
  {
    System.out.println(id);
    System.out.println(dynamoDBMapper.load(Course.class, id));
    return dynamoDBMapper.load(Course.class, id)!= null;
  }
  public Course findByCourseId(String id)
  {
    return dynamoDBMapper.load(Course.class, id);
  }
  public void deleteById(String id) {
    dynamoDBMapper.delete(findByCourseId(id));
  }
}
package com.ninja.RestAPISpringBootWithDynamoDB.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.ninja.RestAPISpringBootWithDynamoDB.entity.Student;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class StudentCRUDRepository implements StudentRepository
{
  private final DynamoDBMapper dynamoDBMapper;

  public StudentCRUDRepository(DynamoDBMapper dynamoDBMapper) {
    this.dynamoDBMapper = dynamoDBMapper;
  }
  public void save(Student Student) {
    dynamoDBMapper.save(Student);
  }

  public Optional<Student> findById(String id) {
    return Optional.ofNullable(dynamoDBMapper.load(Student.class, id));
  }

  public List<Student> findAll()
  {
    DynamoDBScanExpression dynamoDBScanExpression=new DynamoDBScanExpression();
    PaginatedScanList<Student> result = dynamoDBMapper.scan(Student.class,dynamoDBScanExpression);
    return result.subList(0, result.size());
  }

  public boolean existsById(String id)
  {
    return dynamoDBMapper.load(Student.class, id)!= null;
  }
  public Student findByStudentId(String id)
  {
    return dynamoDBMapper.load(Student.class, id);
  }
  public void deleteById(String id) {
    dynamoDBMapper.delete(findByStudentId(id));
  }
  public List<Student> findByCourseEnrolled(String courseName)
  {
    HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
    eav.put(":v1", new AttributeValue().withS(courseName));
    DynamoDBScanExpression dynamoDBScanExpression=new DynamoDBScanExpression()
        .withFilterExpression("begins_with(courseEnrolled,:v1)")
        .withExpressionAttributeValues(eav);
    ;
    PaginatedScanList<Student> result = dynamoDBMapper.scan(Student.class,dynamoDBScanExpression);
    return result.subList(0, result.size());
  }
}
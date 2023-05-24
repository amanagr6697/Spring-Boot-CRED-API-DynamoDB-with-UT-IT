package com.ninja.RestAPISpringBootWithDynamoDB.repository;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.ninja.RestAPISpringBootWithDynamoDB.entity.Student;
import java.util.Optional;
import java.util.List;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.stereotype.Repository;

@EnableScan
@Repository
public interface StudentRepository{
    void save(Student Student);

    Optional<Student> findById(String id);

    List<Student> findAll();

    boolean existsById(String id);

    void deleteById(String id);

    List<Student> findByCourseEnrolled(String courseName);
}


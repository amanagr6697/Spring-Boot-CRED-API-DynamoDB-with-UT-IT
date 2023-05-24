package com.ninja.RestAPISpringBootWithDynamoDB;

import com.ninja.RestAPISpringBootWithDynamoDB.repository.CourseRepository;
import com.ninja.RestAPISpringBootWithDynamoDB.repository.StudentRepository;
import com.ninja.RestAPISpringBootWithDynamoDB.repository.CourseCRUDRepository;
import com.ninja.RestAPISpringBootWithDynamoDB.repository.StudentCRUDRepository;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.springframework.context.annotation.Primary;

@Configuration
	@EnableDynamoDBRepositories
	    (basePackages = "com.ninja.RestAPISpringBootWithDynamoDB.repository")
	public class DynamoDBConfig {

	    @Value("${amazon.dynamodb.endpoint}")
	    private String amazonDynamoDBEndpoint;

	    @Value("${amazon.aws.accesskey}")
	    private String amazonAWSAccessKey;

	    @Value("${amazon.aws.secretkey}")
	    private String amazonAWSSecretKey;

	    @Bean
	    public AmazonDynamoDB amazonDynamoDB(AWSCredentialsProvider awsCredentialsProvider) {
	        AmazonDynamoDB amazonDynamoDB
	            = AmazonDynamoDBClientBuilder.standard()
	            .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(amazonDynamoDBEndpoint, "us-west-2"))
	            .withCredentials(awsCredentialsProvider).build();
	        return amazonDynamoDB;
	    }

	    @Bean
	    public AWSCredentialsProvider awsCredentialsProvider() {
	        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey));
	    }

	@Primary
	@Bean
	public DynamoDBMapperConfig dynamoDBMapperConfig() {

		return DynamoDBMapperConfig.builder()
				.withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.UPDATE)
				.withConsistentReads(DynamoDBMapperConfig.ConsistentReads.EVENTUAL)
				.withTableNameOverride(null)
				.withPaginationLoadingStrategy(DynamoDBMapperConfig.PaginationLoadingStrategy.LAZY_LOADING)
				.build();
	}

//	@Bean
//	@Primary
//	public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB,
//			DynamoDBMapperConfig config) {
//		return new DynamoDBMapper(amazonDynamoDB, config);
//	}

	@Bean
	@Primary
	public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
		return new DynamoDBMapper(amazonDynamoDB);
	}

	@Bean
	public CourseRepository courseRepository(DynamoDBMapper dynamoDBMapper)
	{
		return new CourseCRUDRepository(dynamoDBMapper);
	}

	@Bean
	public StudentRepository studentRepository(DynamoDBMapper dynamoDBMapper)
	{
		return new StudentCRUDRepository(dynamoDBMapper);
	}
}

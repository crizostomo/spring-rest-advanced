package com.developer.beverageapi.core.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {

    @Autowired
    private StorageProperties storageProperties;

    @Bean
    public AmazonS3 amazonS3() {
        var credentials = new BasicAWSCredentials(
                storageProperties.getS3().getIdAccessKey(),
                storageProperties.getS3().getKeySecretAccess());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(storageProperties.getS3().getRegion())
                .build(); // It returns a S3 instance, therefore we can inject in the project and use the methods normally
    }
}

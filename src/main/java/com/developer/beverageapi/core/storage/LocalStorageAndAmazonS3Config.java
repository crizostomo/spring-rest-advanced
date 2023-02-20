package com.developer.beverageapi.core.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.developer.beverageapi.domain.service.PhotoStorageService;
import com.developer.beverageapi.infrastructure.service.storage.LocalPhotoStorageService;
import com.developer.beverageapi.infrastructure.service.storage.S3PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalStorageAndAmazonS3Config {

    @Autowired
    private StorageProperties storageProperties;

//    @Bean
//    @ConditionalOnProperty(name = "beverage.storage.kind", havingValue = "s3")
//    public AmazonS3 amazonS3() {
//        var credentials = new BasicAWSCredentials(
//                storageProperties.getS3().getIdAccessKey(),
//                storageProperties.getS3().getKeySecretAccess());
//
//        return AmazonS3ClientBuilder.standard()
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .withRegion(storageProperties.getS3().getRegion())
//                .build(); // It returns a S3 instance, therefore we can inject in the project and use the methods normally
//    }

    @Bean
    public PhotoStorageService photoStorageService() {
        if (StorageProperties.StorageKind.S3.equals(storageProperties.getKind())) {
            return new S3PhotoStorageService();
        } else {
            return new LocalPhotoStorageService();
        }
    }
}

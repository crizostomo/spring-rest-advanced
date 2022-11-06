package com.developer.beverageapi.infrastructure.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.developer.beverageapi.core.storage.StorageProperties;
import com.developer.beverageapi.domain.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;

@Service
public class S3PhotoStorageService implements PhotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void storage(NewPhoto newPhoto) {

        try {
            String filePath = getFilePath(newPhoto.getFileName());

            var objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(newPhoto.getContentType());

            var putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    filePath,
                    newPhoto.getInputStream(),
                    objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);

            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("It was not possible to send the file to Amazon S3", e);
        }
    }

    private String getFilePath(String fileName) {
        return String.format("%s/%s", storageProperties.getS3().getPhotosDirectory(), fileName);
    }

    @Override
    public PhotoRetrieved recover(String fileName) {
        String filePath = getFilePath(fileName);

        URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), filePath);

        return PhotoRetrieved.builder()
                .url(url.toString())
                .build();

    }

    @Override
    public void remove(String fileName) {
        try {
            String filePath = getFilePath(fileName);

            var deleteObjectRequest = new DeleteObjectRequest(
                    storageProperties.getS3().getBucket(), filePath);

            amazonS3.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            throw new StorageException("It was not possible to delete the file to Amazon S3", e);
        }
    }
}

package com.developer.beverageapi.infrastructure.service.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.developer.beverageapi.domain.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3PhotoStorageService implements PhotoStorageService {

    @Autowired
    private AmazonS3 amazonS3;

    @Override
    public void storage(NewPhoto newPhoto) {

    }

    @Override
    public InputStream recover(String fileName) {
        return null;
    }

    @Override
    public void remove(String fileName) {

    }
}

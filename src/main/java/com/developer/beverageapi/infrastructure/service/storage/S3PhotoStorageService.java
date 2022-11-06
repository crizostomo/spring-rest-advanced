package com.developer.beverageapi.infrastructure.service.storage;

import com.developer.beverageapi.domain.service.PhotoStorageService;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class S3PhotoStorageService implements PhotoStorageService {

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

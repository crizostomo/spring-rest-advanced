package com.developer.beverageapi.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface PhotoStorageService {

    void storage(NewPhoto newPhoto);

    default String generateFileName(String originalName) {
        return UUID.randomUUID().toString() + "_" + originalName;
    }

    @Builder
    @Getter
    class NewPhoto {
        private String fileName;
        private InputStream inputStream;
    }
}

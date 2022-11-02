package com.developer.beverageapi.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;

public interface PhotoStorageService {

    void storage(NewPhoto newPhoto);

    @Builder
    @Getter
    class NewPhoto {
        private String fileName;
        private InputStream inputStream;
    }
}

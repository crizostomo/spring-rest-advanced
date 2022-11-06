package com.developer.beverageapi.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface PhotoStorageService {

    void storage(NewPhoto newPhoto);

//    InputStream recover(String fileName);
    PhotoRetrieved recover(String fileName); // For S3

    void remove(String fileName);

    default void substitute(String oldFileName, NewPhoto newPhoto) {
        this.storage(newPhoto);

        if (oldFileName != null) {
            this.remove(oldFileName);
        }
    }

    default String generateFileName(String originalName) {
        return UUID.randomUUID().toString() + "_" + originalName;
    }

    @Builder
    @Getter
    class NewPhoto {
        private String fileName;
        private String contentType;
        private InputStream inputStream;
    }

    @Builder
    @Getter
    class PhotoRetrieved {

        private InputStream inputStream;
        private String url;

        public boolean isUrlPresent() {
            return url != null;
        }

        public boolean isInputStreamPresent() {
            return inputStream != null;
        }
    }
}

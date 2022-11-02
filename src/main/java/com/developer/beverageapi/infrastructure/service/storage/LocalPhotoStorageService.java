package com.developer.beverageapi.infrastructure.service.storage;

import com.developer.beverageapi.domain.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalPhotoStorageService implements PhotoStorageService {

    @Value("${beverage.storage.local.photos-directory}")
    private Path photosDirectory;

    @Override
    public void storage(NewPhoto newPhoto) {

        try {
        Path filePath = getFilePath(newPhoto.getFileName());

            FileCopyUtils.copy(newPhoto.getInputStream(), Files.newOutputStream(filePath));
        } catch (Exception e) {
            throw new StorageException("It was not possible to storage the file.", e);
        }
    }

    private Path getFilePath(String fileName) {
        return photosDirectory.resolve(Path.of(fileName)); // resolve, to join/return the file path
    }
}

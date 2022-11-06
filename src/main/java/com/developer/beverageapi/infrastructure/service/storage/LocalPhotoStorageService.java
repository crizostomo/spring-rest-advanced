package com.developer.beverageapi.infrastructure.service.storage;

import com.developer.beverageapi.core.storage.StorageProperties;
import com.developer.beverageapi.domain.service.PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;

public class LocalPhotoStorageService implements PhotoStorageService {

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void storage(NewPhoto newPhoto) {

        try {
            Path filePath = getFilePath(newPhoto.getFileName());

            FileCopyUtils.copy(newPhoto.getInputStream(), Files.newOutputStream(filePath));
        } catch (Exception e) {
            throw new StorageException("It was not possible to storage the file.", e);
        }
    }

    @Override
    public PhotoRetrieved recover(String fileName) {
        try {
            Path filePath = getFilePath(fileName);

            PhotoRetrieved photoRetrieved = PhotoRetrieved.builder()
                    .inputStream(Files.newInputStream(filePath))
                    .build();

            return photoRetrieved;
        } catch (Exception e) {
            throw new StorageException("It was not possible to recover the file.", e);
        }
    }

    @Override
    public void remove(String fileName) {
        try {
            Path filePath = getFilePath(fileName);
            Files.deleteIfExists(filePath);
        } catch (Exception e) {
            throw new StorageException("It was not possible to exclude the file.", e);
        }
    }

    private Path getFilePath(String fileName) {
        return storageProperties.getLocal().getPhotosDirectory().resolve(Path.of(fileName)); // resolve, to join/return the file path
    }
}

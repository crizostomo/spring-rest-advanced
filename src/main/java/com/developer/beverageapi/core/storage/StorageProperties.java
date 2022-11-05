package com.developer.beverageapi.core.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Getter
@Setter
@Component
@ConfigurationProperties("beverage.storage")
public class StorageProperties {

    private Local local = new Local();

    @Getter
    @Setter
    public class Local {

        private Path photosDirectory;

    }

    @Getter
    @Setter
    public class S3 {

        private String idAccessKey;
        private String keySecretAccess;
        private String bucket;
        private String region;
        private String photosDirectory;

    }
}

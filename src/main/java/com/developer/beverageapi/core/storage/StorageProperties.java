package com.developer.beverageapi.core.storage;

import com.amazonaws.regions.Regions;
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
    private S3 s3 = new S3();

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
        private Regions region;
        private String photosDirectory;

    }
}

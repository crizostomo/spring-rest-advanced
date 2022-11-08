package com.developer.beverageapi.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

//@Validated
@Getter
@Setter
@Component
@ConfigurationProperties("beverage.email")
public class EmailProperties {

    private Implementation impl = Implementation.FAKE;
    private Sandbox sandbox = new Sandbox();

//    @NotNull
    private String recipient;

    public enum Implementation {
        SMTP, FAKE, SANDOX
    }

    @Getter
    @Setter
    public class Sandbox {
        private String recipient;
    }
}

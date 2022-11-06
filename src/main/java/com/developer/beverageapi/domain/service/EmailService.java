package com.developer.beverageapi.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

public interface EmailService {

    void send(Message message);

    @Getter
    @Builder
    class Message {

        private Set<String> recipient;
        private String subject;
        private String body;
    }
}

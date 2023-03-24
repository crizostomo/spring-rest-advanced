package com.developer.beverageapi.core.security.authorizationserver;

import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.List;

public interface OAuth2AuthorizationQueryService {

    List<RegisteredClient> registeredClientListWithConsent(String principalName);
    List<OAuth2Authorization> authorizationList(String principalName, String clientId);
}

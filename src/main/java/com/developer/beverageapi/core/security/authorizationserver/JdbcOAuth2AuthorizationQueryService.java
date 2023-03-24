package com.developer.beverageapi.core.security.authorizationserver;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.List;

public class JdbcOAuth2AuthorizationQueryService implements OAuth2AuthorizationQueryService {

    private final JdbcOperations jdbcOperations;
    private final RowMapper<RegisteredClient> registeredClientRowMapper;
    private final RowMapper<OAuth2Authorization> oAuth2AuthorizationRowMapper;

    private final String LIST_AUTHORIZED_CLIENTS = "select orc.* from oauth2_authorization_consent oac " +
            "inner join oauth2_registered_client orc on orc.id = oac.registered_client_id " +
            "where oac.principal_name = ? ";

    private final String LIST_AUTHORIZATION_QUERY = "select aut.* from oauth2_authorization aut " +
            "inner join oauth2_registered_client orc on orc.id = aut.registered_client_id " +
            "where aut.principal_name = ? " +
            "and aut.registered_client_id = ? ";

    public JdbcOAuth2AuthorizationQueryService(JdbcOperations jdbcOperations, RegisteredClientRepository repository) {
        this.jdbcOperations = jdbcOperations;
        this.registeredClientRowMapper = new JdbcRegisteredClientRepository.RegisteredClientRowMapper();
        this.oAuth2AuthorizationRowMapper = new JdbcOAuth2AuthorizationService.OAuth2AuthorizationRowMapper(repository);
    }

    @Override
    public List<RegisteredClient> registeredClientListWithConsent(String principalName) {
        return this.jdbcOperations.query(LIST_AUTHORIZED_CLIENTS, registeredClientRowMapper, principalName);
    }

    @Override
    public List<OAuth2Authorization> authorizationList(String principalName, String clientId) {
        return this.jdbcOperations.query(LIST_AUTHORIZATION_QUERY, oAuth2AuthorizationRowMapper, principalName, clientId);
    }
}

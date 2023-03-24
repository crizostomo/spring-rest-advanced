package com.developer.beverageapi.core.security.authorizationserver;

import com.developer.beverageapi.domain.repository.RepositoryUser;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.InputStream;
import java.security.KeyStore;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
public class AuthorizationServerConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
//        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        OAuth2AuthorizationServerConfigurer<HttpSecurity> authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer<>();

        authorizationServerConfigurer.authorizationEndpoint(customizer -> customizer.consentPage("/oauth2/consent"));

        RequestMatcher endpointsMatcher = authorizationServerConfigurer
                .getEndpointsMatcher();

        http.requestMatcher(endpointsMatcher)
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher))
                .apply(authorizationServerConfigurer);

        return http
//                .formLogin(Customizer.withDefaults())
                .formLogin(customizer -> customizer.loginPage("/login"))
                .build();
    }

    @Bean
    public ProviderSettings providerSettings(BeverageSecurityProperties properties) {
        return ProviderSettings.builder()
                .issuer(properties.getProviderUrl())
                .build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder,
                                                                 JdbcOperations jdbcOperations) {

        /**
         * It was commented the lines below because this data will be inserted via afterMigrate
        RegisteredClient beverageBackEnd = RegisteredClient
                .withId("1")
                .clientId("beverage-backend")
                .clientSecret(passwordEncoder.encode("backend123"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("READ")
                .tokenSettings(TokenSettings.builder()
//                        .accessTokenFormat(OAuth2TokenFormat.REFERENCE) // Opaque Token
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED) // JWT Token
                        .accessTokenTimeToLive(Duration.ofMinutes(30))
                        .build())
                .build();

        RegisteredClient beverageWeb = RegisteredClient
                .withId("2")
                .clientId("beverage-web")
                .clientSecret(passwordEncoder.encode("web123"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .scope("READ")
                .scope("WRITE")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                        .accessTokenTimeToLive(Duration.ofMinutes(15))
                        .reuseRefreshTokens(false)
                        .refreshTokenTimeToLive(Duration.ofDays(1))
                        .build())
                .redirectUri("http://127.0.0.1:8080/authorized")
                .redirectUri("http://127.0.0.1:8080/swagger-ui/oauth2-redirect.html")
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(true)
                        .build())
                .build();

        RegisteredClient beverageAnalytics = RegisteredClient
                .withId("3")
                .clientId("beverageAnalytics")
                .clientSecret(passwordEncoder.encode("web123"))
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("READ")
                .scope("WRITE")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                        .accessTokenTimeToLive(Duration.ofMinutes(15))
                        .build())
                .redirectUri("http://www.beverageanalytics.local:8082")
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(false)
                        .build())
                .build();
         */

//        return new InMemoryRegisteredClientRepository(Arrays.asList(beverageBackEnd, beverageWeb, beverageAnalytics));
        JdbcRegisteredClientRepository repository = new JdbcRegisteredClientRepository(jdbcOperations);

        /**
         * It was commented the lines below because this data will be inserted via afterMigrate
        repository.save(beverageBackEnd);
        repository.save(beverageWeb);
        repository.save(beverageAnalytics);
         */

        return repository;
    }

    @Bean
    public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcOperations jdbcOperations,
                                                                 RegisteredClientRepository registeredClientRepository) {

        return new JdbcOAuth2AuthorizationService(
                jdbcOperations, registeredClientRepository
        );
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(JwtKeyStoreProperties properties) throws Exception {
        char[] keyStorePass = properties.getPassword().toCharArray();
        String keypairAlias = properties.getKeypairAlias();

        Resource jksLocation = properties.getJksLocation();
        InputStream inputStream = jksLocation.getInputStream();
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(inputStream, keyStorePass);

        RSAKey rsaKey = RSAKey.load(keyStore, keypairAlias, keyStorePass);

        return new ImmutableJWKSet<>(new JWKSet(rsaKey));
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(RepositoryUser repositoryUser) {
        return context -> {
            Authentication authentication = context.getPrincipal();
            if (authentication.getPrincipal() instanceof User) {
                User userCredentials = (User) authentication.getPrincipal();

                com.developer.beverageapi.domain.model.User user = repositoryUser.findByEmail(userCredentials.getUsername()).orElseThrow();

                Set<String> authorities = new HashSet<>();
                for (GrantedAuthority authority : userCredentials.getAuthorities()) {
                    authorities.add(authority.getAuthority());
                }

                context.getClaims().claim("user_id", user.getId().toString());
                context.getClaims().claim("authorities", authorities);
            }
        };
    }

    @Bean
    public OAuth2AuthorizationConsentService consentService(JdbcOperations jdbcOperations,
                                                            RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationConsentService(jdbcOperations, registeredClientRepository);
    }

    @Bean
    public OAuth2AuthorizationQueryService oAuth2AuthorizationQueryService(JdbcOperations jdbcOperations,
                                                                           RegisteredClientRepository repository) {
        return new JdbcOAuth2AuthorizationQueryService(jdbcOperations, repository);
    }
}

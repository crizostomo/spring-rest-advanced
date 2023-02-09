package com.developer.beverageapi.core.security.authorizationserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtKeyStoreProperties jwtKeyStoreProperties;

//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .jdbc(dataSource);
//                .inMemory()
//                .withClient("beverage-web")
//                .secret(passwordEncoder.encode("web123"))
//                .authorizedGrantTypes("password", "refresh_token") // Type of flow used = password
//                .scopes("WRITE", "READ")
//                .accessTokenValiditySeconds(60 * 60 * 6)
//                .refreshTokenValiditySeconds(60 * 24 * 60 * 60)
//                    .and()
//                .withClient("beverage-analytics")
//                .secret(passwordEncoder.encode("web123"))
//                .authorizedGrantTypes("authorization_code") // It is here that we authorize the client with the scopes
//                .scopes("WRITE", "READ")
//                .redirectUris("http://client-application")
////                .redirectUris("http:/www.beverage-analytics.local:8082") // Class 22.19
//// http://localhost:8081/oauth/authorize?response_type=code&client_id=beverage-analytics&state=abc&redirect_uri=http://
//// client-application
//                    .and()
//                .withClient("webadmin")
//                .authorizedGrantTypes("implicit")
//                .scopes("WRITE", "READ")
//                .redirectUris("http://client-web-application")
//// http://localhost:8081/oauth/authorize?response_type=token&client_id=webadmin&state=abc&redirect_uri=http://
//// client-web-application
//                .and()
//                .withClient("invoice")
//                .secret(passwordEncoder.encode("web123"))
//                .authorizedGrantTypes("client_credentials") // Type of flow used = password
//                .scopes("WRITE", "READ")
//                    .and()
//                .withClient("checktoken")
//                .secret(passwordEncoder.encode("check123")); // The standard time is 12 hours
    }

// http://localhost:8081/oauth/authorize?response_type=code&client_id=beverage-analytics&redirect_uri=http://client-application
// &code_challenge=xxxxxxxxxxx&code_challenge_method=s256
// https://tonyxu-io.github.io/pkce-generator/

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.checkTokenAccess("isAuthenticated()")
        security.checkTokenAccess("permitAll()") // It permits all access
                .tokenKeyAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        var enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(new JwtCustomClaimsTokenEnhancer(), jwtAccessTokenConverter()));

        endpoints.authenticationManager(authenticationManager) // It needs this, otherwise the 'password flow' does not work
                .userDetailsService(userDetailsService)
                .reuseRefreshTokens(false) // It generates another refresh token
//                .tokenStore(redisTokenStore())
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(enhancerChain)
                .approvalStore(approvalStore(endpoints.getTokenStore()))
                .tokenGranter(tokenGranter(endpoints));
    }

    private ApprovalStore approvalStore(TokenStore tokenStore) { // It allows "approve/deny" in oauth2
        var approvalStore = new TokenApprovalStore();
        approvalStore.setTokenStore(tokenStore);

        return approvalStore;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        var jwtAccessTokenConverter = new JwtAccessTokenConverter();
//        jwtAccessTokenConverter.setSigningKey("beveragea45sd4s56f4sd5f4d5g5dsf41d5fg4d5f"); // Symmetric key
        var keyStorePass = jwtKeyStoreProperties.getPassword();
        var keyPairAlias = jwtKeyStoreProperties.getKeypairAlias();

        var keyStoreKeyFactory = new KeyStoreKeyFactory(
                jwtKeyStoreProperties.getJksLocation(), keyStorePass.toCharArray());
        var keyPair = keyStoreKeyFactory.getKeyPair(keyPairAlias);

        jwtAccessTokenConverter.setKeyPair(keyPair); // Asymmetric key

        return jwtAccessTokenConverter;
    }

//    private TokenStore redisTokenStore() {
//        return new RedisTokenStore(redisConnectionFactory);
//    }

    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
                endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory());

        var granters = Arrays.asList(
                pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());

        return new CompositeTokenGranter(granters);
    }
}

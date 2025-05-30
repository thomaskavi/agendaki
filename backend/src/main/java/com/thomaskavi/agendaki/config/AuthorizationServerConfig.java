package com.thomaskavi.agendaki.config;

import java.io.InputStream;
import java.security.KeyStore;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // REMOVA ESTE IMPORT
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2AccessTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.thomaskavi.agendaki.config.customgrant.CustomPasswordAuthenticationConverter;
import com.thomaskavi.agendaki.config.customgrant.CustomPasswordAuthenticationProvider;
import com.thomaskavi.agendaki.config.customgrant.CustomUserAuthorities;

@Configuration
public class AuthorizationServerConfig {

  @Value("${security.jwt.key-store-path}")
  private String keyStorePath;

  @Value("${security.jwt.key-store-password}")
  private String keyStorePassword;

  @Value("${security.jwt.key-alias}")
  private String keyAlias;

  @Value("${security.jwt.key-password}")
  private String keyPassword;

  @Value("${security.client-id}")
  private String clientId;

  @Value("${security.client-secret}")
  private String clientSecret;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;

  public AuthorizationServerConfig(PasswordEncoder passwordEncoder,
      @Lazy UserDetailsService userDetailsService) {
    this.passwordEncoder = passwordEncoder;
    this.userDetailsService = userDetailsService;
  }

  @SuppressWarnings("removal")
  @Bean
  @Order(2)
  SecurityFilterChain asSecurityFilterChain(HttpSecurity http) throws Exception {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

    http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
        .tokenEndpoint(tokenEndpoint -> tokenEndpoint
            .accessTokenRequestConverter(new CustomPasswordAuthenticationConverter())
            .authenticationProvider(new CustomPasswordAuthenticationProvider(authorizationService(), tokenGenerator(),
                userDetailsService, passwordEncoder))); // passwordEncoder e userDetailsService já estão no construtor

    http.oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults()));

    return http.build();
  }

  @Bean
  OAuth2AuthorizationService authorizationService() {
    return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository());
  }

  @Bean
  OAuth2AuthorizationConsentService oAuth2AuthorizationConsentService() {
    return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository());
  }

  @Bean
  RegisteredClientRepository registeredClientRepository() {
    JdbcRegisteredClientRepository jdbcRegisteredClientRepository = new JdbcRegisteredClientRepository(jdbcTemplate);

    @SuppressWarnings("deprecation")
    RegisteredClient oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId(clientId)
        .clientSecret(passwordEncoder.encode(clientSecret))
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        .authorizationGrantType(AuthorizationGrantType.PASSWORD) // Você pode remover esta linha agora
        .redirectUri("http://localhost:5173/home")
        .redirectUri("http://localhost:4200/home")
        .scope(OidcScopes.OPENID)
        .scope(OidcScopes.PROFILE)
        .scope("read")
        .scope("write")
        .clientSettings(ClientSettings.builder()
            .requireAuthorizationConsent(false)
            .requireProofKey(false)
            .build())
        .tokenSettings(TokenSettings.builder()
            .accessTokenTimeToLive(Duration.ofHours(1))
            .refreshTokenTimeToLive(Duration.ofDays(7))
            .reuseRefreshTokens(true)
            .build())
        .build();

    RegisteredClient existingClient = jdbcRegisteredClientRepository.findByClientId(oidcClient.getClientId());
    if (existingClient == null) {
      jdbcRegisteredClientRepository.save(oidcClient);
      System.out.println("Cliente OAuth2 'agendaki-frontend-client' registrado com sucesso no banco de dados.");
    } else {
      System.out.println("Cliente OAuth2 'agendaki-frontend-client' já existe no banco de dados.");
    }

    return jdbcRegisteredClientRepository;
  }

  @Bean
  ClientSettings clientSettings() {
    return ClientSettings.builder().build();
  }

  @Bean
  OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator() {
    NimbusJwtEncoder jwtEncoder = new NimbusJwtEncoder(jwkSource());
    JwtGenerator jwtGenerator = new JwtGenerator(jwtEncoder);
    jwtGenerator.setJwtCustomizer(tokenCustomizer());
    OAuth2AccessTokenGenerator accessTokenGenerator = new OAuth2AccessTokenGenerator();
    return new DelegatingOAuth2TokenGenerator(jwtGenerator, accessTokenGenerator);
  }

  @Bean
  OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
    return context -> {
      OAuth2ClientAuthenticationToken principal = context.getPrincipal();
      // Adicione uma verificação de tipo para evitar ClassCastException caso o
      // principal não seja do seu tipo esperado
      if (principal.getDetails() instanceof CustomUserAuthorities) {
        CustomUserAuthorities user = (CustomUserAuthorities) principal.getDetails();
        List<String> authorities = user.getAuthorities().stream().map(x -> x.getAuthority()).toList();
        if (context.getTokenType().getValue().equals("access_token")) {
          context.getClaims().claim("authorities", authorities).claim("username", user.getUsername());
        }
      } else {
        // Log a warning or handle cases where principal.getDetails() is not
        // CustomUserAuthorities
        System.out.println("WARNING: Principal details are not CustomUserAuthorities. Type: "
            + (principal.getDetails() != null ? principal.getDetails().getClass().getName() : "null"));
      }
    };
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    try {
      System.out.println("DEBUG - KeyStore Path: '" + keyStorePath + "'");
      System.out.println("DEBUG - KeyStore Password Length: " + keyStorePassword.length());
      if (keyStorePassword.length() > 0) {
        System.out.println("DEBUG - KeyStore Password First Char: '" + keyStorePassword.charAt(0) + "'");
      }
      System.out.println("DEBUG - Key Alias: '" + keyAlias + "'");
      System.out.println("DEBUG - Key Password Length: " + keyPassword.length());
      if (keyPassword.length() > 0) {
        System.out.println("DEBUG - Key Password First Char: '" + keyPassword.charAt(0) + "'");
      }

      ClassPathResource resource = new ClassPathResource(keyStorePath);
      if (!resource.exists()) {
        throw new IllegalStateException("Arquivo do KeyStore não encontrado no classpath: " + keyStorePath);
      }
      InputStream is = resource.getInputStream();
      KeyStore keyStore = KeyStore.getInstance("PKCS12");
      keyStore.load(is, keyStorePassword.toCharArray());

      RSAKey rsaKey = RSAKey.load(keyStore, keyAlias, keyPassword.toCharArray());
      JWKSet jwkSet = new JWKSet(rsaKey);

      return new ImmutableJWKSet<>(jwkSet);
    } catch (Exception e) {
      throw new IllegalStateException("Falha ao carregar a JWK Source do KeyStore. Verifique as configurações.", e);
    }
  }
}
package df.web.configs;

import df.base.security.*;
import df.base.security.oauth2.OAuth2UserMapperFactory;
import df.base.security.oauth2.OAuth2UserService;
import df.base.security.oauth2.OpenIDUserService;
import df.base.security.oauth2.mapper.GithubOAuth2UserMapper;
import df.base.security.oauth2.mapper.GoogleOAuth2UserMapper;
import df.base.service.user.UserService;
import df.web.property.HttpSecurityProperties;
import df.web.property.HttpSecurityProperties.FormLoginProperties;
import df.web.property.HttpSecurityProperties.OAuth2Properties;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import static java.util.Arrays.stream;

@SuppressWarnings({"unused"})
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration {

    private final HttpSecurityProperties properties;

    public WebSecurityConfiguration(HttpSecurityProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    private void initialize() { }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userService);

        return provider;
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new UserInfoService(userService);
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            UserDetailsService userDetailsService,
            OAuth2UserService oauth2UserService,
            OpenIDUserService openIDUserService
    ) throws Exception {
        FormLoginProperties          loginProperties  = properties.getFormLogin();
        OAuth2Properties             oauth2Properties = properties.getOAuth2();
        AuthenticationSuccessHandler successHandler   = new UserAuthenticationSuccessHandler(properties);
        AuthenticationFailureHandler failureHandler   = new UserAuthenticationFailureHandler(properties);
        RequestMatcher[]             securityMatchers = stream(properties.getPermitAll())
                .map(AntPathRequestMatcher::new).toArray(RequestMatcher[]::new);

        http.authorizeHttpRequests(authorization -> authorization
                .requestMatchers(securityMatchers).permitAll()
                .anyRequest().authenticated()
        );

        http.exceptionHandling(Customizer.withDefaults());

        http.headers(headers -> headers
                .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        http.userDetailsService(userDetailsService);

        http.formLogin(formLogin -> formLogin
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .loginPage(loginProperties.getBaseUrl())
                .loginProcessingUrl(loginProperties.getProcessingUrl())
                .permitAll()
        );

        http.oauth2Login(oauth2 -> oauth2
                .loginPage(oauth2Properties.getBaseUrl()).permitAll()
                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                        .baseUri(oauth2Properties.getAuthorizationEndpoint()))
                .redirectionEndpoint(redirectionEndpoint -> redirectionEndpoint
                        .baseUri(oauth2Properties.getRedirectEndpoint()))
                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                        .userService(oauth2UserService)
                        .oidcUserService(openIDUserService))
                .successHandler(successHandler)
                .failureHandler(failureHandler)
        );

        http.logout(logout -> logout
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher(loginProperties.getLogoutUrl()))
                .logoutSuccessUrl(loginProperties.getLogoutSuccessUrl())
        );

        return http.build();
    }

    @Bean
    public OAuth2UserMapperFactory userMapperFactory() {
        OAuth2UserMapperFactory factory = new OAuth2UserMapperFactory();

        factory.addMapper(Provider.GOOGLE, new GoogleOAuth2UserMapper());
        factory.addMapper(Provider.GITHUB, new GithubOAuth2UserMapper());

        return factory;
    }

}

package df.web.configuration;

import df.parent.security.oauth2.OAuth2AuthenticationSuccessHandler;
import df.parent.security.oauth2.OAuth2UserService;
import df.parent.security.service.UserSuccessHandlerService;
import df.web.property.HttpSecurityProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userService, PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(userService);

        return provider;
    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return authorities -> authorities.stream()
                .filter(OAuth2UserAuthority.class::isInstance).collect(Collectors.toSet());
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(
            HttpSecurity http, UserDetailsService userDetailsService, OAuth2UserService oauth2UserService
    ) throws Exception {
        HttpSecurityProperties.OAuth2SecurityProperties oauth2Properties = properties.getOAuth2Security();
        AuthenticationSuccessHandler                    successHandler   = new OAuth2AuthenticationSuccessHandler(new UserSuccessHandlerService(), properties);
        HttpSecurityProperties.FormLoginProperties      loginProperties  = properties.getFormLogin();
        RequestMatcher[]                                securityMatchers = Arrays.stream(properties.getPermitAll())
                .map(AntPathRequestMatcher::new).toArray(RequestMatcher[]::new);
        RequestMatcher                                  h2Matcher        = new AntPathRequestMatcher("%s/**".formatted(properties.getH2Console()));

        http.authorizeHttpRequests(authorization -> authorization
                .requestMatchers(securityMatchers).permitAll()
                .anyRequest().authenticated()
        );

        http.headers(headers
                -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http.csrf(csrf
                -> csrf.ignoringRequestMatchers(h2Matcher));
        http.cors(AbstractHttpConfigurer::disable);
        http.userDetailsService(userDetailsService);

        http.oauth2Login(oauth2 -> oauth2
                .loginPage(properties.getLoginPage()).permitAll()
                .authorizationEndpoint(authorizationEndpoint
                        -> authorizationEndpoint.baseUri(oauth2Properties.getAuthorizationEndpoint()))
                .redirectionEndpoint(redirectionEndpoint
                        -> redirectionEndpoint.baseUri(oauth2Properties.getRedirectEndpoint()))
                .userInfoEndpoint(userInfoEndpoint
                        -> userInfoEndpoint.userService(oauth2UserService))
                .successHandler(successHandler)
                .defaultSuccessUrl(loginProperties.getLoginSuccess(), true)
                .failureUrl(loginProperties.getLoginFailure())
        );

        http.logout(logout -> logout
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutRequestMatcher(new AntPathRequestMatcher(loginProperties.getLogout()))
                .logoutSuccessUrl(loginProperties.getLogoutSuccess())
        );

        return http.build();
    }

}

package io.startform.web.configuration;

import io.startform.parent.security.DefaultUserDetailsService;
import io.startform.parent.security.oauth2.OAuth2AuthenticationSuccessHandler;
import io.startform.parent.security.oauth2.OAuth2UserService;
import io.startform.parent.security.service.UserService;
import io.startform.web.property.HttpSecurityProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration {

    private final HttpSecurityProperties properties;

    public WebSecurityConfiguration(HttpSecurityProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    private void initialize() {

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());

        return provider;
    }

    @Bean
    public OAuth2UserService oauth2UserService() {
        return new OAuth2UserService();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2Login().loginPage("/login/oauth2").authorizationEndpoint().baseUri("/goto/")
                .and().redirectionEndpoint().baseUri("/")
                .and().userInfoEndpoint().userService(oauth2UserService())
                .and().successHandler(new OAuth2AuthenticationSuccessHandler(new UserService(), properties))
                .and().headers().frameOptions().disable()
                .and().csrf().ignoringRequestMatchers(properties.getH2Console())
                .and().cors().disable();

        System.out.println(http);

        http.userDetailsService(new DefaultUserDetailsService());

        return http.build();
    }



    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(properties.getWebSecurity().getPermitAll());
    }

    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth.authenticationProvider(authenticationProvider());

        auth.userDetailsService(new DefaultUserDetailsService());

        auth.inMemoryAuthentication().withUser("USER").password(encoder.encode("password"))
                .roles("USER");

        auth.inMemoryAuthentication().withUser("ADMIN").password(encoder.encode("admin"))
                .roles("USER", "ADMIN");

        return auth.build();
    }

}

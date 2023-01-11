package io.startform.web.configuration;

import io.startform.parent.security.oauth2.OAuth2AuthenticationSuccessHandler;
import io.startform.parent.security.oauth2.OAuth2UserService;
import io.startform.parent.security.service.UserService;
import io.startform.web.property.HttpSecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final HttpSecurityProperties properties;
    private final DataSource dataSource;
    private final Environment environment;

    public WebSecurityConfiguration(Environment environment, HttpSecurityProperties properties, DataSource dataSource) {
        this.properties = properties;
        this.dataSource = dataSource;
        this.environment = environment;
    }

    @PostConstruct
    private void initialize() {

    }

    @Bean
    public UserDetailsService userDetailsManager() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();

        manager.setDataSource(dataSource);
        manager.setRolePrefix("ROLE_");

        return manager;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());

        return provider;
    }

    @Bean
    public OAuth2UserService oauth2UserService() {
        return new OAuth2UserService();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .oauth2Login()
                .loginPage("/login/oauth2")
                .authorizationEndpoint()
                    .baseUri("/goto/")
                    .and()
                .redirectionEndpoint()
                    .baseUri("/")
                    .and()
                .userInfoEndpoint()
                    .userService(oauth2UserService())
                    .and()
                .successHandler(
                        new OAuth2AuthenticationSuccessHandler(new UserService(), properties)
                )
                    .and()
//                .formLogin()
//                .loginPage(properties.getFormLogin().getLogin()).permitAll()
//                .usernameParameter(properties.getFormLogin().getUsername())
//                .passwordParameter(properties.getFormLogin().getPassword())
//                .loginProcessingUrl(properties.getFormLogin().getLogin())
                // remember-me configuration
//                .and()
//                .rememberMe()
//                .rememberMeParameter(properties.getRememberMe().getParameterName())
//                .rememberMeCookieName(properties.getRememberMe().getCookieName())
//                .key(properties.getRememberMe().getSecretKey())
//                .tokenValiditySeconds(properties.getRememberMe().getValiditySeconds())
//                // log-out configuration
//                .and()
//                .logout().logoutUrl(properties.getFormLogin().getLogout())
//                .deleteCookies(properties.getSessionCookie())
                // others

                .headers()
                    .frameOptions().disable()
                    .and()
                .csrf()
                    .ignoringAntMatchers(properties.getH2Console())
                    .and()
                .cors().disable();
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().antMatchers(properties.getWebSecurity().getPermitAll());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        auth.authenticationProvider(authenticationProvider());

        auth
                .inMemoryAuthentication()
                .withUser("USER")
                .password(encoder.encode("password"))
                .roles("USER");

        auth
                .inMemoryAuthentication()
                .withUser("ADMIN")
                .password(encoder.encode("admin"))
                .roles("USER", "ADMIN");
    }

    private RequestMatcher requestMatcherPort(final int port) {

        return (HttpServletRequest request) -> {
            System.out.println("Web check - port " + port);
         return    port == request.getLocalPort();
        };
    }

}

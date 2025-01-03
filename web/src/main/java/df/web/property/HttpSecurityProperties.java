package df.web.property;

import df.application.property.AuthorizationRedirectProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "http-security")
public class HttpSecurityProperties implements AuthorizationRedirectProperties {

    private String[] permitAll;
    private String h2Console;
    private String securityPath;

    private RememberMeProperties rememberMe;
    private FormLoginProperties  formLogin;
    private OAuth2Properties     oauth2;

    private String sessionCookie;
    private String successRedirect;

    public String[] getPermitAll() {
        return permitAll;
    }

    public void setPermitAll(String[] permitAll) {
        this.permitAll = permitAll;
    }

    public String getH2Console() {
        return h2Console;
    }

    public void setH2Console(String h2Console) {
        this.h2Console = h2Console;
    }

    public OAuth2Properties getOAuth2() {
        return oauth2;
    }

    public void setOAuth2(OAuth2Properties oauth2Security) {
        this.oauth2 = oauth2Security;
    }

    public FormLoginProperties getFormLogin() {
        return formLogin;
    }

    public void setFormLogin(FormLoginProperties formLogin) {
        this.formLogin = formLogin;
    }

    public RememberMeProperties getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(RememberMeProperties rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getSessionCookie() {
        return sessionCookie;
    }

    public void setSessionCookie(String sessionCookie) {
        this.sessionCookie = sessionCookie;
    }

    public String getSecurityPath() {
        return securityPath;
    }

    public void setSecurityPath(String securityPath) {
        this.securityPath = securityPath;
    }

    @Override
    public String getOAuth2SuccessRedirect() {
        return getOAuth2().getSuccessUrl();
    }

    @Override
    public String getOAuth2FailureRedirect() {
        return getOAuth2().getFailureUrl();
    }

    @Override
    public String getFormLoginSuccessRedirect() {
        return getFormLogin().getSuccessUrl();
    }

    @Override
    public String getFormLoginFailureRedirect() {
        return getFormLogin().getFailureUrl();
    }

    public void setSuccessRedirect(String successRedirect) {
        this.successRedirect = successRedirect;
    }

    public static class FormLoginProperties {

        private String processingUrl;
        private String baseUrl;
        private String successUrl;
        private String failureUrl;
        private String logoutUrl;
        private String logoutSuccessUrl;
        private String username;
        private String password;

        public String getProcessingUrl() {
            return processingUrl;
        }

        public void setProcessingUrl(String processingUrl) {
            this.processingUrl = processingUrl;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getLogoutUrl() {
            return logoutUrl;
        }

        public void setLogoutUrl(String logoutUrl) {
            this.logoutUrl = logoutUrl;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSuccessUrl() {
            return successUrl;
        }

        public void setSuccessUrl(String successUrl) {
            this.successUrl = successUrl;
        }

        public String getLogoutSuccessUrl() {
            return logoutSuccessUrl;
        }

        public String getFailureUrl() {
            return failureUrl;
        }

        public void setFailureUrl(String failureUrl) {
            this.failureUrl = failureUrl;
        }

        public void setLogoutSuccessUrl(String logoutSuccessUrl) {
            this.logoutSuccessUrl = logoutSuccessUrl;
        }

    }

    public static class RememberMeProperties {

        private String cookieName;
        private String parameterName;
        private String secretKey;
        private int    validitySeconds;

        public String getCookieName() {
            return cookieName;
        }

        public void setCookieName(String cookieName) {
            this.cookieName = cookieName;
        }

        public String getParameterName() {
            return parameterName;
        }

        public void setParameterName(String parameterName) {
            this.parameterName = parameterName;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public int getValiditySeconds() {
            return validitySeconds;
        }

        public void setValiditySeconds(int validitySeconds) {
            this.validitySeconds = validitySeconds;
        }

    }

    public static class OAuth2Properties {

        public String redirectEndpoint;
        public String authorizationEndpoint;
        public String successUrl;
        public String failureUrl;
        public String baseUrl;

        public String getRedirectEndpoint() {
            return redirectEndpoint;
        }

        public void setRedirectEndpoint(String processingEndpoint) {
            this.redirectEndpoint = processingEndpoint;
        }

        public String getAuthorizationEndpoint() {
            return authorizationEndpoint;
        }

        public void setAuthorizationEndpoint(String authorizationEndpoint) {
            this.authorizationEndpoint = authorizationEndpoint;
        }

        public String getSuccessUrl() {
            return successUrl;
        }

        public void setSuccessUrl(String successUrl) {
            this.successUrl = successUrl;
        }

        public String getFailureUrl() {
            return failureUrl;
        }

        public void setFailureUrl(String failureUrl) {
            this.failureUrl = failureUrl;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }

}

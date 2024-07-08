package df.web.property;

import df.parent.property.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;

import static java.lang.String.format;

@ConfigurationProperties(prefix = "http-security")
public class HttpSecurityProperties implements SecurityProperties {

    private String[] permitAll;
    private String h2Console;
    private String securityPath;
    private String oauth2Path;
    private String loginPage;

    private RememberMeProperties rememberMe;
    private FormLoginProperties formLogin;
    private OAuth2SecurityProperties oauth2Security;

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

    public String getOauth2Path() {
        return oauth2Path;
    }

    public void setOauth2Path(String oauth2Path) {
        this.oauth2Path = oauth2Path;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public OAuth2SecurityProperties getOAuth2Security() {
        return oauth2Security;
    }

    public void setOAuth2Security(OAuth2SecurityProperties oauth2Security) {
        this.oauth2Security = oauth2Security;
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
    public String getSuccessRedirect() {
        return successRedirect;
    }

    public void setSuccessRedirect(String successRedirect) {
        this.successRedirect = successRedirect;
    }

    @Override
    public String toString() {
        return "HttpSecurityProperties{permitAll=%s, securityPath=%s, h2Console='%s', rememberMe=%s, formLogin=%s, sessionCookie='%s', successRedirect='%s', webSecurity=%s, oauth2Security=%s}"
                .formatted(Arrays.toString(permitAll), securityPath, h2Console, rememberMe, formLogin, sessionCookie, successRedirect, oauth2Security);
    }

    public static class FormLoginProperties {

        private String processingUrl;
        private String login;
        private String loginSuccess;
        private String loginFailure;
        private String logout;
        private String logoutSuccess;
        private String username;
        private String password;

        public String getProcessingUrl() {
            return processingUrl;
        }

        public void setProcessingUrl(String processingUrl) {
            this.processingUrl = processingUrl;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getLogout() {
            return logout;
        }

        public void setLogout(String logout) {
            this.logout = logout;
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

        public String getLoginSuccess() {
            return loginSuccess;
        }

        public void setLoginSuccess(String loginSuccess) {
            this.loginSuccess = loginSuccess;
        }

        public String getLogoutSuccess() {
            return logoutSuccess;
        }

        public String getLoginFailure() {
            return loginFailure;
        }

        public void setLoginFailure(String loginFailure) {
            this.loginFailure = loginFailure;
        }

        public void setLogoutSuccess(String logoutSuccess) {
            this.logoutSuccess = logoutSuccess;
        }

        @Override
        public String toString() {
            return "FormLoginProperties{processingUrl='%s', login='%s', loginSuccess='%s', logout='%s', logoutSuccess='%s', username='%s', password='%s'}"
                    .formatted(processingUrl, login, loginSuccess, logout, logoutSuccess, username, password);
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

        @Override
        public String toString() {
            return format("RememberMe{cookieName='%s', parameterName='%s', secretKey='%s', validitySeconds=%d}",
                    cookieName, parameterName, secretKey, validitySeconds);
        }
    }

    public static class OAuth2SecurityProperties {

        public String redirectEndpoint;
        public String authorizationEndpoint;

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

        @Override
        public String toString() {
            return "OAuth2SecurityProperties{redirectEndpoint='%s', authorizationEndpoint='%s'}"
                    .formatted(redirectEndpoint, authorizationEndpoint);
        }

    }

}

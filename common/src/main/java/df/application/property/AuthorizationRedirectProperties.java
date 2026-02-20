package df.application.property;

public interface AuthorizationRedirectProperties {

    String getFormLoginSuccessRedirect();

    String getFormLoginFailureRedirect();

    String getOAuth2SuccessRedirect();

    String getOAuth2FailureRedirect();

}

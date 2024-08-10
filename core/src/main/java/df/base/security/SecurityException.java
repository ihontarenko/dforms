package df.base.security;

import org.springframework.security.core.AuthenticationException;

public class SecurityException extends AuthenticationException {

    public SecurityException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SecurityException(String msg) {
        super(msg);
    }

}

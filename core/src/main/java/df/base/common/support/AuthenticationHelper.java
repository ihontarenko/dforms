package df.base.common.support;

import df.base.jpa.User;
import df.base.security.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SuppressWarnings({"unused"})
public class AuthenticationHelper {

    private static final Authentication AUTHENTICATION
            = SecurityContextHolder.getContext().getAuthentication();

    public static boolean hasRole(String role) {
        return AUTHENTICATION.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }

    public static boolean isPrincipal(String userId) {
        return ((UserInfo)AUTHENTICATION.getPrincipal()).getUser().getId().equals(userId);
    }

    public static UserInfo getPrincipal() {
        return (UserInfo)AUTHENTICATION.getPrincipal();
    }

    public static User getAuthorizedUser() {
        return getPrincipal().getUser();
    }

}

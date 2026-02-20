package df.application.security.helper;

import df.application.persistence.entity.user.User;
import df.application.security.UserInfo;
import org.jmouse.core.SingletonSupplier;
import org.jmouse.core.Verify;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.function.Supplier;

@SuppressWarnings({"unused"})
public class AuthenticationHelper {

    private static final Supplier<Authentication> AUTHENTICATION_SUPPLIER = SingletonSupplier.of(
            () -> SecurityContextHolder.getContext().getAuthentication()
    );

    public static boolean hasRole(String role) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }

    public static boolean noRole(String role) {
        return !hasRole(role);
    }

    public static boolean isPrincipal(String userId) {
        return ((UserInfo) getAuthentication().getPrincipal()).getUser().getId().equals(userId);
    }

    public static UserInfo getPrincipal() {
        return (UserInfo)getAuthentication().getPrincipal();
    }

    public static User getAuthorizedUser() {
        return getPrincipal().getUser();
    }

    public static String getUserId() {
        return getAuthorizedUser().getId();
    }

    public static Authentication getAuthentication() {
        return Verify.nonNull(AUTHENTICATION_SUPPLIER.get(), "AUTHENTICATION_SUPPLIER.get()");
    }

}

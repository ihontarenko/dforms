package df.base.security;

import df.base.persistence.entity.user.User;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class UserInfo implements UserDetails, OAuth2User, OidcUser, AuthenticatedPrincipal {

    private String                                 username;
    private String                                 name;
    private String                                 password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object>                    attributes;
    private Map<String, Object>                    claims;
    private User                                   user;

    public static UserInfo create(User user, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes) {
        UserInfo userInfo = create(user.getEmail(), user.getName(), user.getPassword(), authorities, attributes, Map.of());

        userInfo.setUser(user);

        return userInfo;
    }

    public static UserInfo create(String email, String name, String password,
                                  Collection<? extends GrantedAuthority> authorities,
                                  Map<String, Object> attributes, Map<String, Object> claims) {
        UserInfo userInfo = new UserInfo();

        userInfo.username = email;
        userInfo.name = name;
        userInfo.password = password;
        userInfo.authorities = authorities;
        userInfo.attributes = attributes;
        userInfo.claims = claims;

        return userInfo;
    }

    public static UserInfo create(String email, String name, String password,
                                  Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes) {
        return create(email, name, password, authorities, attributes, Map.of());
    }

    public static UserInfo create(String email, String name, String password,
                                  Collection<? extends GrantedAuthority> authorities) {
        return create(email, name, password, authorities, Map.of(), Map.of());
    }

    public static UserInfo create(String email, String name, String password) {
        return create(email, name, password, Set.of(), Map.of(), Map.of());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Map<String, Object> getClaims() {
        return claims;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserInfo{username='%s', name='%s', authorities=%s}".formatted(username, name, authorities);
    }
}

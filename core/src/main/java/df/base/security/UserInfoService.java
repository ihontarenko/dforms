package df.base.security;

import df.base.jpa.User;
import df.base.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserInfoService implements UserDetailsService, UserDetailsPasswordService {

    private final UserService userService;

    public UserInfoService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.loadUserByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username %s not found".formatted(username));
        }

        return UserInfo.create(user.getEmail(), user.getLogin(), user.getPassword(), userService.getAuthorities(user));
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

}

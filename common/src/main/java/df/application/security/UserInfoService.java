package df.application.security;

import df.application.persistence.entity.user.User;
import df.application.service.user.UserService;
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
        User user = userService.loadUserEmailAndProvider(username, Provider.LOCAL)
                .orElseThrow(() -> new UsernameNotFoundException("Username %s not found".formatted(username)));

        UserInfo userInfo = UserInfo.create(user.getEmail(), user.getName(), user.getPassword(),
                userService.getAuthorities(user));

        userInfo.setUser(user);

        return userInfo;
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

}

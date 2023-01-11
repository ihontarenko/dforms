package io.startform.parent.security.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public void processOAuthPostLogin(String username) {
        System.out.println(username);
//        User existUser = repo.getUserByUsername(username);
//
//        if (existUser == null) {
//            User newUser = new User();
//            newUser.setUsername(username);
//            newUser.setProvider(Provider.GOOGLE);
//            newUser.setEnabled(true);
//
//            repo.save(newUser);
//        }

    }


}

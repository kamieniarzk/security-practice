package com.example.securitypractice.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.securitypractice.security.UserRole.*;

@Repository("fake")
public class FakeUserDaoService implements UserDao {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> selectUserByUsername(String username) {
        return getUsers().stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst();
    }

    private List<User> getUsers() {
        return Lists.newArrayList(
                new User(MANAGER.getGrantedAuthorities(), "michaelscott",
                        passwordEncoder.encode("password"),
                        true, true, true, true),
                new User(SALESMAN.getGrantedAuthorities(), "dwightschrute",
                        passwordEncoder.encode("password"),
                        true, true, true, true),
                new User(ACCOUNTANT.getGrantedAuthorities(), "kevinmalone",
                        passwordEncoder.encode("password"),
                        true, true, true, true)
        );

    }
}

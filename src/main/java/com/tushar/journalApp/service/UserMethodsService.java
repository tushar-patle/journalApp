package com.tushar.journalApp.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.tushar.journalApp.entity.User;
import com.tushar.journalApp.repository.UserRepository;

@Component
public class UserMethodsService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private static final Logger logger = LoggerFactory.getLogger(UserMethodsService.class);

    // fetch all the user entries
    public List<User> getAllUserEntries() {
        return userRepository.findAll();
    }

    // save new user with password
    public void saveNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error caused by ", e);
        }
    }

    // save admin with password
    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }

    // save/update new entries for user
    public void saveEntry(User user) {
        userRepository.save(user);
    }

    // update existing entry for user by username (can be used in get user by username)
    public User getAllUserEntries(String username) {
        return userRepository.findByUsername(username);
    }

    // delete existing entry from user by username
    public void deleteEntry(User user) {
        userRepository.delete(user);
    }
}

package com.tushar.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tushar.journalApp.entity.User;
import com.tushar.journalApp.service.UserMethodsService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserMethodsService userMethodsService;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // fetch all user entries for Admin
    /*@GetMapping()
    public ResponseEntity<?> getAllUserEntries() {
        List<User> allUserEntries = userMethodsService.getAllUserEntries();
        return new ResponseEntity<>(allUserEntries, HttpStatus.OK);
    }*/

    // fetch user entry of authenticated and authorized user
    @GetMapping
    public ResponseEntity<?> getUserEntry() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntry = userMethodsService.getAllUserEntries(username);
        return new ResponseEntity<>(userEntry, HttpStatus.OK);
    }

    // update existing user details of authenticated and authorized user
    @PutMapping
    public ResponseEntity<User> updateUserEntry(@RequestBody User newUserEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User oldUserEntry = userMethodsService.getAllUserEntries(username);
        oldUserEntry.setUsername(newUserEntry.getUsername());
        oldUserEntry.setPassword(passwordEncoder.encode(newUserEntry.getPassword()));
        userMethodsService.saveEntry(oldUserEntry);
        return new ResponseEntity<>(oldUserEntry, HttpStatus.OK);
    }

    // delete existing entry of authenticated and authorized user
    @DeleteMapping
    public ResponseEntity<?> deleteUserEntry() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userEntry = userMethodsService.getAllUserEntries(username);
        userMethodsService.deleteEntry(userEntry);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

package com.tushar.journalApp.controller;

import com.tushar.journalApp.entity.User;
import com.tushar.journalApp.service.UserMethodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserMethodsService userMethodsService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> allUserEntries = userMethodsService.getAllUserEntries();
        if (allUserEntries != null && !allUserEntries.isEmpty()) {
            return new ResponseEntity<>(allUserEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody User user) {
        userMethodsService.saveNewUser(user);
    }
}

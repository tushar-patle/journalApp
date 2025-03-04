package com.tushar.journalApp.controller;

import com.tushar.journalApp.entity.User;
import com.tushar.journalApp.service.UserMethodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserMethodsService userMethodsService;

    // create new entry in user
    @PostMapping("/create-user")
    public ResponseEntity<User> createUserEntry(@RequestBody User newUserEntry) {
        try {
            userMethodsService.saveNewUser(newUserEntry);
            return new ResponseEntity<>(newUserEntry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // create new entry in user
    @PostMapping("/create-admin")
    public ResponseEntity<User> createAdminEntry(@RequestBody User newAdminEntry) {
        try {
            userMethodsService.saveAdmin(newAdminEntry);
            return new ResponseEntity<>(newAdminEntry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/health-check")
    public String healthCheck() {
        return "Ok";
    }
}

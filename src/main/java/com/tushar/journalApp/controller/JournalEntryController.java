package com.tushar.journalApp.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tushar.journalApp.service.UserMethodsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tushar.journalApp.entity.JournalEntry;
import com.tushar.journalApp.entity.User;
import com.tushar.journalApp.service.JournalEntryMethodsService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryMethodsService journalEntryMethodsService;

    @Autowired
    private UserMethodsService userMethodsService;
    // fetch all the journal entries of authorized and authenticated user
    @GetMapping
    public ResponseEntity<?> getAllJournalEntries() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JournalEntry> allJournalEntries = journalEntryMethodsService.getAllJournalEntries(username);
        if (allJournalEntries != null && !allJournalEntries.isEmpty()) {
            return new ResponseEntity<>(allJournalEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // fetch all the journal entries from authorized and authenticated user username by id
    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getAllJournalEntries(@PathVariable ObjectId id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userMethodsService.getAllUserEntries(username);
        List<JournalEntry> collectId = user.getJournalEntry().stream().filter(x -> x.equals(id)).collect(Collectors.toList());
        if(!collectId.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryMethodsService.getAllJournalEntries(username, id);
            if(journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // to create new entries in journal for authorized and authenticated user
    @PostMapping
    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry newJournalEntry) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            journalEntryMethodsService.saveJournalEntry(newJournalEntry, username);
            return new ResponseEntity<>(newJournalEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // to update journal entries by id for authorized and authenticated user
    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntry(@PathVariable ObjectId id,
                                                           @RequestBody JournalEntry updateJournalEntry) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userMethodsService.getAllUserEntries(username);
        List<JournalEntry> collectId = user.getJournalEntry().stream().filter(x -> x.equals(id)).collect(Collectors.toList());
        if(!collectId.isEmpty()) {
            // Optional<JournalEntry> journalEntry = journalEntryMethodsService.getAllJournalEntries(username, id);
            JournalEntry oldJournalEntry = journalEntryMethodsService.getAllJournalEntries(username,id).orElse(null);
            if (oldJournalEntry != null) {
                if (updateJournalEntry.getTitle() != null && !updateJournalEntry.getTitle().equals("")) {
                    oldJournalEntry.setTitle(updateJournalEntry.getTitle());
                }
                if (updateJournalEntry.getContent() != null && !updateJournalEntry.getContent().equals("")) {
                    oldJournalEntry.setContent(updateJournalEntry.getContent());
                }
                journalEntryMethodsService.saveJournalEntry(oldJournalEntry, username);
                return new ResponseEntity<>(oldJournalEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // to delete journal entries by id from authorized and authenticated user
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isRemoved = journalEntryMethodsService.deleteJournalEntry(id, username);
        if (isRemoved) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // to delete all journal entries from authorized and authenticated user
    @DeleteMapping
    public ResponseEntity<?> deleteJournalEntry() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isRemoved = journalEntryMethodsService.deleteJournalEntries(username);
        if(isRemoved) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

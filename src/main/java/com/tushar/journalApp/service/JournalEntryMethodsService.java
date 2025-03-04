package com.tushar.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tushar.journalApp.entity.JournalEntry;
import com.tushar.journalApp.entity.User;
import com.tushar.journalApp.repository.JournalEntryRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JournalEntryMethodsService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserMethodsService userMethodsService;

    // fetch all the journal entries of user
    public List<JournalEntry> getAllJournalEntries(String username) {
        User user = userMethodsService.getAllUserEntries(username);
        return user.getJournalEntry();
    }
    public Optional<JournalEntry> getAllJournalEntries(ObjectId id) {
        return journalEntryRepository.findById(id);
    }
    public Optional<JournalEntry> getAllJournalEntries(String username, ObjectId id) {
        User user = userMethodsService.getAllUserEntries(username);
        if(user.getUsername().equals(username)) {
            return journalEntryRepository.findById(id);
        }
        return Optional.empty();
    }

    // to create new entries/ update existing entries in journal for user with username
    @Transactional
    public void saveJournalEntry(JournalEntry journalEntry, String username) {
        try {
            User user = userMethodsService.getAllUserEntries(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntry().add(savedJournalEntry);
            userMethodsService.saveEntry(user);
        } catch (Exception e) {
            log.error("Exception", e);
            throw new RuntimeException("An error occurred while saving the entry ", e);
        }
    }

    // to delete journal entries from user username by id
    @Transactional
    public boolean deleteJournalEntry(ObjectId id, String username) {
        boolean isRemoved = false;
        try {
            User user = userMethodsService.getAllUserEntries(username);
            isRemoved = user.getJournalEntry().removeIf(x -> x.getId().equals(id));
            if (isRemoved) {
                userMethodsService.saveEntry(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting the entry.", e);
        }
        return isRemoved;
    }

    // to delete all journal entries from user username
    @Transactional
    public boolean deleteJournalEntries(String username) {
        boolean isRemoved = false;
        try {
            User user = userMethodsService.getAllUserEntries(username);
            List<JournalEntry> allJournalEntries = user.getJournalEntry();
            isRemoved = user.getJournalEntry().removeAll(allJournalEntries);
            if(isRemoved) {
                userMethodsService.saveEntry(user);
                journalEntryRepository.deleteAll(allJournalEntries);
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting the entry ", e);
        }
        return isRemoved;
    }
}

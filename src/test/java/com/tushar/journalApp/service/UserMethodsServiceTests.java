/*
package com.tushar.journalApp.service;

import com.tushar.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserMethodsServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JournalEntryMethodsService journalEntryMethodsService;

    @Disabled
    @Test
    public void testFindByUsername() {
        assertNotNull(userRepository.findByUsername("TusharOne"));
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "10,9,19"
    })
    public void test(int a, int b, int c) {
        assertEquals(c, a+b);
    }
}
*/

/*
package com.tushar.journalApp.service;

import com.tushar.journalApp.entity.User;
import com.tushar.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTest() {
        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(User.builder().username("TusharOne").password("jhjvjkjhkk").roles(new ArrayList<>()).build());
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername("TusharOne");
        Assertions.assertNotNull(userDetails);
    }
}
*/

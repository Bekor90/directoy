package com.nisum.directory.john.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.nisum.directory.john.entity.Users;
import com.nisum.directory.john.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void shouldSaveUserSuccessfully() {
        Users user = new Users();
        when(userRepository.save(any(Users.class))).thenReturn(user);

        Users saved = userRepository.save(user);

        assertNotNull(saved);
        verify(userRepository, times(1)).save(any(Users.class));
    }
}
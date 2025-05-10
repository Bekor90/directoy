package com.nisum.directory.john.service;

import com.nisum.directory.john.dto.request.PhoneRequest;
import com.nisum.directory.john.dto.request.UserRequest;
import com.nisum.directory.john.dto.response.UserResponse;
import com.nisum.directory.john.entity.Users;
import com.nisum.directory.john.security.exception.RequiredValueException;
import com.nisum.directory.john.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private JwtService jwtService;

    @InjectMocks private UserService userService;

    private final String validEmail = "juan@dominio.cl";
    private final String validPassword = "Hunter123";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(userService, "passwordRegex", "^[A-Za-z\\d]{6,}$");
        ReflectionTestUtils.setField(userService, "emailRegex", "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private UserRequest buildValidRequest() {
        PhoneRequest phone = new PhoneRequest("1234567", "1", "57");
        return new UserRequest("Juan Rodriguez", validEmail, validPassword, List.of(phone));
    }

    @Test
    void registerUser_shouldSucceed_whenInputIsValid() {
        UserRequest request = buildValidRequest();

        when(userRepository.existsByEmail(validEmail)).thenReturn(false);
        when(jwtService.generateToken(validEmail)).thenReturn("jwt-token");

        UserResponse response = userService.registerUser(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.token());
        verify(userRepository).save(any(Users.class));
    }

    @Test
    void registerUser_shouldFail_whenEmailExists() {
        UserRequest request = buildValidRequest();

        when(userRepository.existsByEmail(validEmail)).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser(request));
        assertEquals("email already exists", ex.getMessage());
    }

    @Test
    void registerUser_shouldFail_whenPasswordInvalid() {
        UserRequest request = new UserRequest("Juan", validEmail, "123", List.of(
                new PhoneRequest("1234567", "1", "57")));

        when(userRepository.existsByEmail(validEmail)).thenReturn(false);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser(request));
        assertEquals("Password without format required", ex.getMessage());
    }

    @Test
    void registerUser_shouldFail_whenEmailInvalid() {
        UserRequest request = new UserRequest("Juan", "juan@@dominio", validPassword, List.of(
                new PhoneRequest("1234567", "1", "57")));

        when(userRepository.existsByEmail("juan@@dominio")).thenReturn(false);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser(request));
        assertEquals("Email without format required", ex.getMessage());
    }

    @Test
    void registerUser_shouldFail_whenMissingFields() {
        UserRequest request = new UserRequest(null, validEmail, validPassword, List.of());

        RequiredValueException ex = assertThrows(RequiredValueException.class,
                () -> userService.registerUser(request));
        assertEquals("incomplete data", ex.getMessage());
    }
}

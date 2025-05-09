package com.nisum.directory.john.service;

import com.nisum.directory.john.dto.request.UserRequest;
import com.nisum.directory.john.dto.response.UserResponse;
import com.nisum.directory.john.entity.Phone;
import com.nisum.directory.john.entity.Users;
import com.nisum.directory.john.repository.UserRepository;
import com.nisum.directory.john.util.ArgumentValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Value("${app.password.regex}")
    private String passwordRegex;

    @Value("${app.regex.email}")
    private String emailRegex;

    private static final String MESSAGE_FIELD_INCOMPLETE = "incomplete data";

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public UserResponse registerUser(UserRequest request) {

        validateUser(request);
        UUID userId = UUID.randomUUID();
        String token = jwtService.generateToken(request.email());
        LocalDateTime now = LocalDateTime.now();

        Users users = new Users();
        users.setId(userId);
        users.setName(request.name());
        users.setEmail(request.email());
        users.setPassword(request.password());
        users.setCreated(now);
        users.setModified(now);
        users.setLastLogin(now);
        users.setToken(token);
        users.setActive(true);

        List<Phone> phones = request.phones().stream().map(p -> {
            Phone phone = new Phone();
            phone.setNumber(p.number());
            phone.setCitycode(p.citycode());
            phone.setContrycode(p.contrycode());
            phone.setUser(users);
            return phone;
        }).toList();

        users.setPhones(phones);
        userRepository.save(users);

        return new UserResponse(users.getId(), now, now, now, token, true);
    }

    private void validateUser(UserRequest request) {

        ArgumentValidator.validateFieldString(request.name(), MESSAGE_FIELD_INCOMPLETE);
        ArgumentValidator.validateFieldString(request.email(), MESSAGE_FIELD_INCOMPLETE);
        ArgumentValidator.validateFieldString(request.password(), MESSAGE_FIELD_INCOMPLETE);
        ArgumentValidator.validateFieldPhones(request.phones(), MESSAGE_FIELD_INCOMPLETE);

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("email already exists");
        }

        if (!request.password().matches(passwordRegex)) {
            throw new IllegalArgumentException("Password without format required");
        }

        if(!request.email().matches(emailRegex)) {
            throw new IllegalArgumentException("Email without format required");
        }
    }
}

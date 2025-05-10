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

        validateFieldUser(request);
        String token = jwtService.generateToken(request.email());
        LocalDateTime now = LocalDateTime.now();

        Users users = Users.builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .created(now)
                .modified(now)
                .lastLogin(now)
                .token(token)
                .isActive(true)
                .build();

        List<Phone> phones = request.phones().stream()
                .map(p -> Phone.builder()
                        .number(p.number())
                        .citycode(p.citycode())
                        .contrycode(p.contrycode())
                        .users(users)
                        .build())
                .toList();
        users.setPhones(phones);
        userRepository.save(users);

        return new UserResponse(users.getId(), now, now, now, token, true);
    }

    private void validateFieldUser(UserRequest request) {

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

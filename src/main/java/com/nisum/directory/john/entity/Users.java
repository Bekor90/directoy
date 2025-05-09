
package com.nisum.directory.john.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @Column(name = "user_id")
    private UUID id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private LocalDateTime created;
    private LocalDateTime modified;
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    private String token;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "phones")
    private List<Phone> phones;
}

package com.nisum.directory.john.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "phone")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private String citycode;
    private String contrycode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;
}

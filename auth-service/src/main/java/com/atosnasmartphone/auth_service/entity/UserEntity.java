package com.atosnasmartphone.auth_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "mst_user_smartphone")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false)
    private String password;
}

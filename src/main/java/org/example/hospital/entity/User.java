package org.example.hospital.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.hospital.enums.Role;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String email;
    private String password;
    private String role;
}

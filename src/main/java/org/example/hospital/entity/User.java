package org.example.hospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;
import org.example.hospital.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Data
@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String email;
    private String username;
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

//    @Override
//    @JsonIgnore
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role.name()));
//    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (role.name().equals("ADMIN")) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        if (role.name().equals("PATIENT")) {
            authorities.add(new SimpleGrantedAuthority("PATIENT"));
        }
        if (role.name().equals("DOCTOR")) {
            authorities.add(new SimpleGrantedAuthority("DOCTOR"));
        }
        if (role.name().equals("LAB_ASSISTANT")) {
            authorities.add(new SimpleGrantedAuthority("LAB_ASSISTANT"));
        }
        return authorities;
    }
}

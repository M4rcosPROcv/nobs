package com.example.nobs.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "custom_user")
@AllArgsConstructor
public class CustomUser {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}

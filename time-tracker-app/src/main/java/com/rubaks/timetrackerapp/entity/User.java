package com.rubaks.timetrackerapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(nullable = false)
        private String username;
        @Column(nullable = false)
        private String firstName;
        @Column(nullable = false)
        private String lastName;
        @Column(nullable = false, unique = true, length = 320)
        private String email;
        @JsonIgnore
        @Column(nullable = false, length = 1000)
        private String password;

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private UserRole role;

        @Column(nullable = false, updatable = false)
        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;

        @PrePersist
        protected void onCreate() {
                createdAt = LocalDateTime.now();
        }

        @PreUpdate
        protected void onUpdate() {
                updatedAt = LocalDateTime.now();
        }

    }


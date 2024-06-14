package com.kukilabs.demoJDBC.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.Instant;

@Setter
@Getter
@Data
@Entity
@Table(name = "User", schema = "montrack")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
    @SequenceGenerator(name = "user_id_gen", sequenceName = "user_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull(message = "name empty")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull(message = "email empty")
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull(message = "password empty")
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "pin")
    private String pin;

    @NotNull(message = "language empty")
    @Column(name = "language_id", nullable = false)
    private int languageId;


    @Column(name = "profile_img_url")
    private String profileImgUrl;

    @Column(name = "quotes")
    private String quotes;

    @Column(name = "get_started")
    private Instant getStarted;

    @NotNull(message = "created_at empty")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull(message = "updated_at empty")
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;
}

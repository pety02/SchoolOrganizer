package com.example.schoolorganizer.model;

import jakarta.persistence.*;

@Entity
@Table
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passwordId;
    @Column(length = 1000)
    private String passwordHash;

    public Password() {
    }

    public Password(final Password password) {
        this(password.getPasswordHash());
    }

    public Password(final String passwordHash) {
        setPasswordHash(passwordHash);
    }

    public Long getPasswordId() {
        return passwordId;
    }

    public void setPasswordId(Long passwordId) {
        this.passwordId = passwordId;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
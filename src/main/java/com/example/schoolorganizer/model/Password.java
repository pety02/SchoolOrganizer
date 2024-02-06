package com.example.schoolorganizer.model;

import com.example.schoolorganizer.security.PasswordHasher;
import jakarta.persistence.*;

@Entity
@Table
public class Password {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long passwordId;
    @Column(length = 1000)
    private String passwordHash;
    @OneToOne(fetch = FetchType.EAGER)
    private User owner;

    public Password() {
    }

    public Password(final String passwordHash, final User owner) {

        setPasswordHash(passwordHash);
        setOwner(owner);
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
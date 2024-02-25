package com.example.schoolorganizer.model;

import jakarta.persistence.*;

/**
 * This class describes the Users' passwords.
 *
 * @author Petya Licheva
 */
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

    /**
     * Default constructor of Password class.
     */
    public Password() {
    }

    /**
     * General purpose constructor of Password class.
     *
     * @param passwordHash hash of the password.
     * @param owner        the owner of the password.
     */
    public Password(final String passwordHash, final User owner) {

        setPasswordHash(passwordHash);
        setOwner(owner);
    }

    /**
     * Get method for the Password's id.
     *
     * @return the id of the Password from the database.
     */
    public Long getPasswordId() {
        return passwordId;
    }

    /**
     * Set method for the Password's id.
     * Used to set the id of the Password when the Hibernate ORM generates it.
     *
     * @param passwordId the Password's id.
     */
    public void setPasswordId(Long passwordId) {
        this.passwordId = passwordId;
    }

    /**
     * Get method for the Password hash.
     *
     * @return the hash of the Password.
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Set method for the Password hash.
     *
     * @param passwordHash the Password hash.
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Get method for the Password owner.
     *
     * @return the owner of the Password.
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Set method for the owner of the Password.
     *
     * @param owner the owner of the Password.
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }
}
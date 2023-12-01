package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @ManyToMany
    private Set<User> users;

    public UserRole() {
    }
    public UserRole(final UserRole that) {
        this(that.getName(), that.getUsers());
    }
    public UserRole(final String name, final Set<User> users) {
        this.name = name;
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}

package com.example.schoolorganizer.model;

import java.util.*;

import jakarta.persistence.*;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;
    @Column(length = 60)
    private String name;
    @Column(length = 100)
    private String surname;
    @Column(unique = true, length = 300, nullable = false)
    private String email;
    @Column(unique = true, length = 150, nullable = false)
    private String username;
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Task> tasks;
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Notebook> notebooks;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> friends;

    /* TODO: to implement a method that insert definite User (person Y)
        to friendsList of other User (person X) and send him/her request
        for friendship. If he/she (person Y) disagree it, the first User (person X)
        removes person Y from his/her friendsList or else he/her adds you as friend. */
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    public User() {

    }

    public User(final String name, final String surname, final String email,
                final String username, final List<Task> tasks,
                final List<Notebook> notebooks, final List<User> friends,
                final Set<UserRole> roles) {
        setName(name);
        setSurname(surname);
        setEmail(email);
        setUsername(username);
        setTasks(tasks);
        setNotebooks(notebooks);
        setFriends(friends);
        setRoles(roles);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Notebook> getNotebooks() {
        return notebooks;
    }

    public void setNotebooks(List<Notebook> notebooks) {
        this.notebooks = notebooks;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
package com.example.schoolorganizer.model;

import java.util.*;

import jakarta.persistence.*;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(length = 60)
    private String name;
    @Column(length = 100)
    private String surname;
    @Column(nullable = false, unique = true, length = 300)
    private String email;
    @Column(nullable = false, unique = true, length = 150)
    private String username;
    @Column(nullable = false, length = 150)
    private String password;
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
    @ManyToMany
    private Set<UserRole> roles;
    public User() {

    }

    public User(final String name, final String surname, final String email,
                final String username, final String password, final List<Task> tasks,
                final List<Notebook> notebooks, final List<User> friends,
                final Set<UserRole> roles) {
        setName(name);
        setSurname(surname);
        setEmail(email);
        setUsername(username);
        setPassword(password);
        setTasks(tasks);
        setNotebooks(notebooks);
        setFriends(friends);
    }

    public User(final User that) {
        this(that.getName(), that.getSurname(), that.getEmail(),
                that.getUsername(), that.getPassword(), that.getTasks(),
                that.getNotebooks(), that.getFriends(), that.getRoles());
        setUserId(that.getUserId());
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        if(userId <= 0) {
            throw new IllegalArgumentException("Invalid userId!");
        }

        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public void setTasks(final List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    public List<Notebook> getNotebooks() {
        return new ArrayList<>(notebooks);
    }

    public void setNotebooks(final List<Notebook> notebooks) {
        this.notebooks = new ArrayList<>(notebooks);
    }

    public List<User> getFriends() {
        return new ArrayList<>(friends);
    }

    public void setFriends(final List<User> friends) {
        this.friends = new ArrayList<>(friends);
    }
    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
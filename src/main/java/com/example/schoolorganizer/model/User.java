package com.example.schoolorganizer.model;

import java.util.*;

import jakarta.persistence.*;

/**
 * This class describes a user.
 *
 * @author Petya Licheva
 */
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
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<CalendarEvent> events;

    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    /**
     * Default constructor of the User class.
     */
    public User() {

    }

    /**
     * Genera purpose constructor of the User class.
     *
     * @param name      the first name of the User.
     * @param surname   the surname of the User.
     * @param email     the email of the User.
     * @param username  the username of the User.
     * @param tasks     the list of User tasks.
     * @param notebooks the list of User notebooks.
     * @param friends   the list of User friends.
     * @param roles     the list of User roles.
     */
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

    /**
     * Get method for the User id.
     *
     * @return the User id.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Set method for the User id. It is used to set
     * the User id when Hibernate ORM generates it.
     *
     * @param userId the User id.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Get method for the User first name.
     *
     * @return the User first name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set method for the User first name.
     *
     * @param name the User first name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get method for the User surname.
     *
     * @return the User surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Set method for the User surname.
     *
     * @param surname the User surname.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Get method for the User email.
     *
     * @return the User email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set method for the User email.
     *
     * @param email the User email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get method for the User username.
     *
     * @return the User username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set method for the User username.
     *
     * @param username the User username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get method for the list of tasks created by the User.
     *
     * @return the list of tasks created by the User.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Set method for the list of tasks created by the User.
     *
     * @param tasks the list of tasks created by the User.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Get method for the list of notebooks created by the User.
     *
     * @return the list of notebooks created by the User.
     */
    public List<Notebook> getNotebooks() {
        return notebooks;
    }

    /**
     * Set method for the list of notebooks created by the User.
     *
     * @param notebooks the list of notebooks created by the User.
     */
    public void setNotebooks(List<Notebook> notebooks) {
        this.notebooks = notebooks;
    }

    /**
     * Get method for the list of friends connected with the User.
     *
     * @return the list of friends connected with the User.
     */
    public List<User> getFriends() {
        return friends;
    }

    /**
     * Set method for the list of friends connected with the User.
     *
     * @param friends the list of friends connected with the User.
     */
    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    /**
     * Get method for the set of roles connected with the User.
     *
     * @return the set of roles connected with the User.
     */
    public Set<UserRole> getRoles() {
        return roles;
    }

    /**
     * Set method for the set of roles connected with the User.
     *
     * @param roles the set of roles connected with the User.
     */
    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
}
package com.example.schoolorganizer.model;

import java.util.List;
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
    private List<User> friendsList;
    /* TODO: to implement a method that insert definite User (person Y)
        to friendsList of other User (person X) and send him/her request
        for friendship. If he/she (person Y) disagree it, the first User (person X)
        removes person Y from his/her friendsList or else he/her adds you as friend. */
}
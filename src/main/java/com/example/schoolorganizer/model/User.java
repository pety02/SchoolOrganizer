package com.example.schoolorganizer.model;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "USERS", schema = "SCHOOL_ORGANIZER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String password;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private List<Task> tasks;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private List<Notebook> notebooks;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private List<User> friendsList;
    /* TODO: to implement a method that insert definite User (person Y)
        to friendsList of other User (person X) and send him/her request
        for friendship. If he/she (person Y) disagree it, the first User (person X)
        removes person Y from his/her friendsList or else he/her adds you as friend. */
}
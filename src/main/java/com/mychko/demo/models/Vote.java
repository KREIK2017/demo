package com.mychko.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Candidate candidate;

    public Vote() {}

    public Vote(User user, Candidate candidate) {
        this.user = user;
        this.candidate = candidate;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public Candidate getCandidate() { return candidate; }
}

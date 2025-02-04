package com.mychko.demo.models;
import jakarta.persistence.*;

@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private int votes;

    public Candidate() {}

    public Candidate(String name) {
        this.name = name;
        this.votes = 0;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getVotes() { return votes; }
    public void setVotes(int votes) { this.votes = votes; }
}

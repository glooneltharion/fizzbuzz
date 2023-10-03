package com.glooneltharion.fizzbuzz.model;


import jakarta.persistence.*;

@Entity
public class FizzBuzz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int loopStart;

    @Column(nullable = false)
    private int loopEnd;

    private String divisors;
    private String words;

    @Column(nullable = false)
    private String secretCode;

    public FizzBuzz() {
    }

    public FizzBuzz(int loopStart, int loopEnd, String divisors, String words) {
        this.loopStart = loopStart;
        this.loopEnd = loopEnd;
        this.divisors = divisors;
        this.words = words;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLoopStart() {
        return loopStart;
    }

    public void setLoopStart(int start) {
        this.loopStart = start;
    }

    public int getLoopEnd() {
        return loopEnd;
    }

    public void setLoopEnd(int end) {
        this.loopEnd = end;
    }

    public String getDivisors() {
        return divisors;
    }

    public void setDivisors(String divisors) {
        this.divisors = divisors;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }
}


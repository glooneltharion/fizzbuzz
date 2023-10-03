package com.glooneltharion.fizzbuzz.dto;


public class FizzBuzzDto {
    private int start;
    private int end;

    private String divisors;
    private String words;

    public FizzBuzzDto() {
    }

    public FizzBuzzDto(int start, int end, String divisors, String words) {
        this.start = start;
        this.end = end;
        this.divisors = divisors;
        this.words = words;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
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
}

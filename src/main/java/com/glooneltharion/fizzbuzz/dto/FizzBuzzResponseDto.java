package com.glooneltharion.fizzbuzz.dto;

import java.util.List;

public class FizzBuzzResponseDto {
    private List<String> results;

    public FizzBuzzResponseDto() {
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }
}


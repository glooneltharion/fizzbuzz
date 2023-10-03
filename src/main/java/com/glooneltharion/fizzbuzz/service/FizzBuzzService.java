package com.glooneltharion.fizzbuzz.service;

import com.glooneltharion.fizzbuzz.dto.FizzBuzzDto;
import com.glooneltharion.fizzbuzz.model.FizzBuzz;
import com.glooneltharion.fizzbuzz.dto.FizzBuzzResponseDto;

import java.util.List;
import java.util.Optional;

public interface FizzBuzzService {

    List<FizzBuzzDto> getAllFizzBuzzRecords();

    Optional<FizzBuzzDto> getFizzBuzzById(Long id);

    String addFizzBuzz(FizzBuzzDto fizzBuzzDto);

    Optional<FizzBuzzDto> updateFizzBuzzBySecretCode(String secretCode, FizzBuzzDto updatedFizzBuzz);

    void deleteFizzBuzzBySecretCode(String secretCode);

    Optional<FizzBuzz> getFizzBuzzBySecretCode(String secretCode);

    FizzBuzzResponseDto generateFizzBuzz(FizzBuzz fizzBuzz);
}

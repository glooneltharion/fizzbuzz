package com.glooneltharion.fizzbuzz.service;

import com.glooneltharion.fizzbuzz.dto.FizzBuzzDto;
import com.glooneltharion.fizzbuzz.error.CustomIndexOutOfBoundsException;
import com.glooneltharion.fizzbuzz.error.InvalidDivisorException;
import com.glooneltharion.fizzbuzz.model.FizzBuzz;
import com.glooneltharion.fizzbuzz.dto.FizzBuzzResponseDto;
import com.glooneltharion.fizzbuzz.repository.FizzBuzzRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FizzBuzzServiceImpl implements FizzBuzzService {

    private final FizzBuzzRepository fizzBuzzRepository;

    @Autowired
    public FizzBuzzServiceImpl(FizzBuzzRepository fizzBuzzRepository) {
        this.fizzBuzzRepository = fizzBuzzRepository;
    }

    @Override
    public List<FizzBuzzDto> getAllFizzBuzzRecords() {
        List<FizzBuzz> allFizzBuzz = fizzBuzzRepository.findAll();
        return allFizzBuzz.stream()
                .map(this::convertToFizzBuzzDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FizzBuzzDto> getFizzBuzzById(Long id) {
        Optional<FizzBuzz> optionalFizzBuzz = fizzBuzzRepository.findById(id);
        return optionalFizzBuzz.map(this::convertToFizzBuzzDto);
    }

    @Override
    public String addFizzBuzz(FizzBuzzDto fizzBuzzDto) {
        FizzBuzz fizzBuzz = new FizzBuzz(
                fizzBuzzDto.getStart(), fizzBuzzDto.getEnd(), fizzBuzzDto.getDivisors(), fizzBuzzDto.getWords()
        );
        String secretCode = generateSecretCode();
        fizzBuzz.setSecretCode(secretCode);
        fizzBuzzRepository.save(fizzBuzz);
        return fizzBuzz.getSecretCode();
    }

    @Override
    public Optional<FizzBuzzDto> updateFizzBuzzBySecretCode(String secretCode, FizzBuzzDto updatedFizzBuzz) {
        Optional<FizzBuzz> optionalFizzBuzz = fizzBuzzRepository.findBySecretCode(secretCode);

        if (optionalFizzBuzz.isPresent()) {
            FizzBuzz existingFizzBuzz = optionalFizzBuzz.get();
            existingFizzBuzz.setLoopStart(updatedFizzBuzz.getStart());
            existingFizzBuzz.setLoopEnd(updatedFizzBuzz.getEnd());
            existingFizzBuzz.setDivisors(updatedFizzBuzz.getDivisors());
            existingFizzBuzz.setWords(updatedFizzBuzz.getWords());

            fizzBuzzRepository.save(existingFizzBuzz);
            return Optional.of(convertToFizzBuzzDto(existingFizzBuzz));
        } else {
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public void deleteFizzBuzzBySecretCode(String secretCode) {
        fizzBuzzRepository.deleteBySecretCode(secretCode);
    }

    @Override
    public Optional<FizzBuzz> getFizzBuzzBySecretCode(String secretCode) {
        return fizzBuzzRepository.findBySecretCode(secretCode);
    }

    @Override
    public FizzBuzzResponseDto generateFizzBuzz(FizzBuzz fizzBuzz) {
        List<String> results = new ArrayList<>();
        List<String> divisors = List.of(fizzBuzz.getDivisors().split(" "));
        List<String> words = List.of(fizzBuzz.getWords().split(" "));
        FizzBuzzResponseDto fizzBuzzResponse = new FizzBuzzResponseDto();

        for (int i = fizzBuzz.getLoopStart(); i <= fizzBuzz.getLoopEnd(); i++) {
            StringBuilder output = new StringBuilder();
            boolean isDivisible = false;

            try {
                for (int j = 0; j < divisors.size(); j++) {
                    int divisor = Integer.parseInt(divisors.get(j));

                    if (divisor == 0) {
                        throw new InvalidDivisorException("Invalid divisor (0) encountered.");
                    }

                    if (i % divisor == 0) {
                        output.append(words.get(j));
                        isDivisible = true;
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                throw new CustomIndexOutOfBoundsException("Index out of bounds error occurred.");
            }

            if (!isDivisible) {
                output.append(i);
            }

            results.add(output.toString());
        }

        fizzBuzzResponse.setResults(results);

        return fizzBuzzResponse;
    }

    private String generateSecretCode() {
        int code = (int) (Math.random() * 10000);

        return String.format("%04d", code);
    }

    private FizzBuzzDto convertToFizzBuzzDto(FizzBuzz fizzBuzz) {
        FizzBuzzDto fizzBuzzDto = new FizzBuzzDto();
        fizzBuzzDto.setStart(fizzBuzz.getLoopStart());
        fizzBuzzDto.setEnd(fizzBuzz.getLoopEnd());
        fizzBuzzDto.setDivisors(fizzBuzz.getDivisors());
        fizzBuzzDto.setWords(fizzBuzz.getWords());
        return fizzBuzzDto;
    }
}


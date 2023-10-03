package com.glooneltharion.fizzbuzz.repository;

import com.glooneltharion.fizzbuzz.model.FizzBuzz;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class FizzBuzzRepositoryTest {

    @Autowired
    private FizzBuzzRepository fizzBuzzRepository;

    @Test
    public void testFindBySecretCode() {
        FizzBuzz fizzBuzz = new FizzBuzz(1, 100, "3 5", "Fizz Buzz");
        fizzBuzz.setSecretCode("3535");
        fizzBuzzRepository.save(fizzBuzz);
        Optional<FizzBuzz> optionalFizzBuzz = fizzBuzzRepository.findBySecretCode("3535");
        assertTrue(optionalFizzBuzz.isPresent());
        FizzBuzz foundFizzBuzz = optionalFizzBuzz.get();
        assertEquals(1, foundFizzBuzz.getLoopStart());
        assertEquals(100, foundFizzBuzz.getLoopEnd());
        assertEquals("3 5", foundFizzBuzz.getDivisors());
        assertEquals("Fizz Buzz", foundFizzBuzz.getWords());
    }

    @Test
    public void testDeleteBySecretCode() {
        FizzBuzz fizzBuzz = new FizzBuzz();
        fizzBuzz.setSecretCode("1234");
        fizzBuzzRepository.save(fizzBuzz);
        Optional<FizzBuzz> savedFizzBuzz = fizzBuzzRepository.findBySecretCode("1234");
        assertTrue(savedFizzBuzz.isPresent());
        fizzBuzzRepository.deleteBySecretCode("1234");
        Optional<FizzBuzz> deletedFizzBuzz = fizzBuzzRepository.findBySecretCode("1234");
        assertFalse(deletedFizzBuzz.isPresent());
    }
}

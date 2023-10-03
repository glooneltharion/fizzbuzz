package com.glooneltharion.fizzbuzz.service;

import com.glooneltharion.fizzbuzz.dto.FizzBuzzDto;
import com.glooneltharion.fizzbuzz.model.FizzBuzz;
import com.glooneltharion.fizzbuzz.repository.FizzBuzzRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FizzBuzzServiceImplTest {

    @Mock
    private FizzBuzzRepository fizzBuzzRepository;

    @InjectMocks
    private FizzBuzzServiceImpl fizzBuzzService;

    List<FizzBuzz> fizzBuzzes;

    @BeforeEach
    void setUp() {
        fizzBuzzes = List.of(
                new FizzBuzz(1, 100, "3 5", "Fizz Buzz"),
                new FizzBuzz(1, 200, "3 5 4 7", "Fizz Buzz Haha Hihi"),
                new FizzBuzz(1, 100, "0 5", "Fizz Buzz"),
                new FizzBuzz(1, 100, "3 5 7", "Fizz Buzz")
        );

        for (int i = 0; i < fizzBuzzes.size() ; i++) {
            fizzBuzzes.get(i).setId(i + 1L);
            fizzBuzzes.get(i).setSecretCode(String.format("%04d", i));
        }
    }

    @Test
    public void testGetAllFizzBuzzRecords() {
        when(fizzBuzzRepository.findAll()).thenReturn(fizzBuzzes);
        List<FizzBuzzDto> result = fizzBuzzService.getAllFizzBuzzRecords();
        assertEquals(fizzBuzzes.size(), result.size());
        for (int i = 0; i < fizzBuzzes.size(); i++) {
            FizzBuzz expectedFizzBuzz = fizzBuzzes.get(i);
            FizzBuzzDto actualFizzBuzzDto = result.get(i);

            assertEquals(expectedFizzBuzz.getLoopStart(), actualFizzBuzzDto.getStart());
            assertEquals(expectedFizzBuzz.getLoopEnd(), actualFizzBuzzDto.getEnd());
            assertEquals(expectedFizzBuzz.getDivisors(), actualFizzBuzzDto.getDivisors());
            assertEquals(expectedFizzBuzz.getWords(), actualFizzBuzzDto.getWords());
        }
    }

    @Test
    public void testGetFizzBuzzById() {
        Long id = 1L;
        FizzBuzz fizzBuzz = fizzBuzzes.get(0);
        when(fizzBuzzRepository.findById(id)).thenReturn(Optional.of(fizzBuzz));
        Optional<FizzBuzzDto> result = fizzBuzzService.getFizzBuzzById(id);
        assertTrue(result.isPresent());
        assertEquals(fizzBuzz.getLoopStart(), result.get().getStart());
        assertEquals(fizzBuzz.getLoopEnd(), result.get().getEnd());
        assertEquals(fizzBuzz.getDivisors(), result.get().getDivisors());
        assertEquals(fizzBuzz.getWords(), result.get().getWords());
    }

    @Test
    public void testAddFizzBuzz() {
        FizzBuzzDto fizzBuzzDto = new FizzBuzzDto();
        when(fizzBuzzRepository.save(any(FizzBuzz.class))).thenAnswer(invocation -> {
            FizzBuzz savedFizzBuzz = invocation.getArgument(0);
            savedFizzBuzz.setSecretCode("1234");
            return savedFizzBuzz;
        });
        String secretCode = fizzBuzzService.addFizzBuzz(fizzBuzzDto);
        assertNotNull(secretCode);
        assertEquals(4, secretCode.length());
    }

    @Test
    public void testUpdateFizzBuzzBySecretCode() {
        String secretCode = "0000";
        FizzBuzzDto updatedFizzBuzz = new FizzBuzzDto();
        updatedFizzBuzz.setStart(50);
        updatedFizzBuzz.setEnd(150);
        updatedFizzBuzz.setDivisors("3 5 4 7");
        updatedFizzBuzz.setWords("Fizz Buzz Hezz Nazz");

        FizzBuzz existingFizzBuzz = fizzBuzzes.get(0);

        when(fizzBuzzRepository.findBySecretCode(secretCode)).thenReturn(Optional.of(existingFizzBuzz));
        when(fizzBuzzRepository.save(any(FizzBuzz.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<FizzBuzzDto> result = fizzBuzzService.updateFizzBuzzBySecretCode(secretCode, updatedFizzBuzz);

        assertTrue(result.isPresent());
        assertEquals(updatedFizzBuzz.getStart(), result.get().getStart());
        assertEquals(updatedFizzBuzz.getEnd(), result.get().getEnd());
        assertEquals(updatedFizzBuzz.getDivisors(), result.get().getDivisors());
        assertEquals(updatedFizzBuzz.getWords(), result.get().getWords());
    }

    @Test
    public void testDeleteFizzBuzzBySecretCode() {
        String secretCode = "1234";
        doNothing().when(fizzBuzzRepository).deleteBySecretCode(secretCode);

        fizzBuzzService.deleteFizzBuzzBySecretCode(secretCode);

        verify(fizzBuzzRepository, times(1)).deleteBySecretCode(secretCode);
    }

}





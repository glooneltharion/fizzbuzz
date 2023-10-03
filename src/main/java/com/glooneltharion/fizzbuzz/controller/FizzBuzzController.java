package com.glooneltharion.fizzbuzz.controller;

import com.glooneltharion.fizzbuzz.dto.FizzBuzzDto;
import com.glooneltharion.fizzbuzz.dto.FizzBuzzRequestDto;
import com.glooneltharion.fizzbuzz.error.CustomIndexOutOfBoundsException;
import com.glooneltharion.fizzbuzz.error.InvalidDivisorException;
import com.glooneltharion.fizzbuzz.model.FizzBuzz;
import com.glooneltharion.fizzbuzz.dto.FizzBuzzResponseDto;
import com.glooneltharion.fizzbuzz.service.FizzBuzzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fizzbuzz")
public class FizzBuzzController {

    private final FizzBuzzService fizzBuzzService;

    @Autowired
    public FizzBuzzController(FizzBuzzService fizzBuzzService) {
        this.fizzBuzzService = fizzBuzzService;
    }

    @GetMapping()
    public ResponseEntity<?> getFizzBuzz(@RequestParam(required = false) Long id) {
        if (id != null) {
            Optional<FizzBuzzDto> optionalFizzBuzzDto = fizzBuzzService.getFizzBuzzById(id);
            if (optionalFizzBuzzDto.isPresent()) {
                return ResponseEntity.ok(optionalFizzBuzzDto.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            List<FizzBuzzDto> fizzBuzzList = fizzBuzzService.getAllFizzBuzzRecords();
            return ResponseEntity.ok(fizzBuzzList);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addFizzBuzz(@RequestBody FizzBuzzDto fizzBuzzDto) {
        String secretCode = fizzBuzzService.addFizzBuzz(fizzBuzzDto);
        return new ResponseEntity<>(secretCode, HttpStatus.CREATED);
    }

    @DeleteMapping ("/delete")
    public ResponseEntity<?> deleteFizzBuzzBySecretCode(@RequestBody FizzBuzzRequestDto fizzBuzzRequestDto) {
        String secretCode = fizzBuzzRequestDto.getSecretCode();
        Optional<FizzBuzz> optionalFizzBuzz = fizzBuzzService.getFizzBuzzBySecretCode(secretCode);

        if (optionalFizzBuzz.isPresent()) {
            try {
                fizzBuzzService.deleteFizzBuzzBySecretCode(secretCode);
                return ResponseEntity.ok("FizzBuzz record deleted successfully.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete FizzBuzz record.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FizzBuzz not found for the given secret code.");
        }
    }

    @PutMapping("/update/{secretCode}")
    public ResponseEntity<?> updateFizzBuzzBySecretCode(
            @PathVariable String secretCode,
            @RequestBody FizzBuzzDto fizzBuzzDto) {
        Optional<FizzBuzzDto> updatedFizzBuzzOptional = fizzBuzzService.updateFizzBuzzBySecretCode(secretCode, fizzBuzzDto);

        if (updatedFizzBuzzOptional.isPresent()) {
            return ResponseEntity.ok(updatedFizzBuzzOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FizzBuzz not found for the given secret code.");
        }
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateFizzBuzz(@RequestBody FizzBuzzRequestDto fizzBuzzRequestDto) {
        String secretCode = fizzBuzzRequestDto.getSecretCode();
        Optional<FizzBuzz> optionalFizzBuzz = fizzBuzzService.getFizzBuzzBySecretCode(secretCode);

        if (optionalFizzBuzz.isPresent()) {
            FizzBuzz fizzBuzz = optionalFizzBuzz.get();
            try {
                FizzBuzzResponseDto response = fizzBuzzService.generateFizzBuzz(fizzBuzz);
                return ResponseEntity.ok(response);
            } catch (CustomIndexOutOfBoundsException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            } catch (InvalidDivisorException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FizzBuzz not found for the given secret code.");
        }
    }
}


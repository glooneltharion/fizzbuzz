package com.glooneltharion.fizzbuzz.controller;

import com.glooneltharion.fizzbuzz.repository.FizzBuzzRepository;
import com.glooneltharion.fizzbuzz.service.FizzBuzzService;
import com.glooneltharion.fizzbuzz.service.FizzBuzzServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FizzBuzzControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FizzBuzzRepository fizzBuzzRepository;

    private FizzBuzzService fizzBuzzService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        fizzBuzzService = new FizzBuzzServiceImpl(fizzBuzzRepository);
    }

    @Test
    public void testGetFizzBuzzById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/fizzbuzz")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}

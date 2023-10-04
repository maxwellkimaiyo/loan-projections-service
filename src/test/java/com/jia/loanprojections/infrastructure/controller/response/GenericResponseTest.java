package com.jia.loanprojections.infrastructure.controller.response;

import com.jia.loanprojections.infrastructure.controller.dto.CustomErrorsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GenericResponseTest {

    private GenericResponse<String> genericResponse;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @BeforeEach
    void setUp() {
        genericResponse = new GenericResponse<>("Success", "data");
        genericResponse.addCustomErrorMessage(new CustomErrorsDto(LocalDateTime.parse("04-10-2023 10:05:51", formatter),"Error 1", "BAD_REQUEST"));
        genericResponse.addCustomErrorMessage(new CustomErrorsDto(LocalDateTime.parse("04-10-2023 10:05:51", formatter),"Error 2", "BAD_REQUEST"));
    }

    @Test
    @DisplayName("Test constructor and getters")
    void testConstructorAndGetters() {
        assertEquals("Success", genericResponse.getMessage());
        assertEquals("data", genericResponse.getData());

        List<CustomErrorsDto> errors = genericResponse.getErrors();
        assertEquals(2, errors.size());
        assertEquals("Error 1", errors.get(0).getMessage());
        assertEquals("BAD_REQUEST", errors.get(1).getStatus());
    }


}

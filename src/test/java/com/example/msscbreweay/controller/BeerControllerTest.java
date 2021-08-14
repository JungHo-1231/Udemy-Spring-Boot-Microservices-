package com.example.msscbreweay.controller;

import com.example.msscbreweay.service.BeerService;
import com.example.msscbreweay.web.model.BeerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @MockBean
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    BeerDto validBeer;

    @BeforeEach
    void setUp() {
        validBeer = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("Beer1")
                .beerStyle("PALE_ALE")
                .upc(123456789012L)
                .build();
    }

    @Test
    void getBeer() throws Exception {
        given(beerService.getBeerById(any(UUID.class))).willReturn(validBeer);

        mockMvc.perform(get("/api/v1/beer/" + validBeer.getId().toString())
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(validBeer.getId().toString()))
                .andExpect(jsonPath("$.beerName").value("Beer1")
                );
    }

    @Test
    void handlerPost() throws Exception {
        //given
        BeerDto beerDto = validBeer;

        beerDto.setId(null);

        BeerDto savedDto = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("New Beer")
                .build();

        // ObjectMapper => java Object to Json
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        given(beerService.savedNewBeer(any())).willReturn(savedDto);

        mockMvc.perform(post("/api/v1/beer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJson)
                )
                .andDo(print())
                .andExpect(status().isCreated())
        ;
    }


    @Test
    void handleUpdate() throws Exception {

        // ObjectMapper => java Object to Json
        String beerDtoJson = objectMapper.writeValueAsString(validBeer);

        mockMvc.perform(put("/api/v1/beer/" + validBeer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJson)
                )
                .andDo(print())
                .andExpect(status().isNoContent())
        ;

        then(beerService).should().updateBeer(validBeer.getId(), validBeer);
//        then(beerService).should().updateBeer(any(UUID.class), any(BeerDto.class));
//        then(beerService).should().updateBeer(any(), any());
    }
}
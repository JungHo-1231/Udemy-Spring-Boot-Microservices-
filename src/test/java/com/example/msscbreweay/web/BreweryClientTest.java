package com.example.msscbreweay.web;

import com.example.msscbreweay.web.model.BeerDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient client;


    @Test
    void getBeerById() {
        BeerDto dto = client.getBeerById(UUID.randomUUID());
        assertNotNull(dto);
    }

    @Test
    void testSaveNewBeer() throws Exception {
        BeerDto beerDto = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("New Beer")
                .build();

        URI uri = client.saveNewBeer(beerDto);

        assertThat(uri.toString()).isEqualTo("/api/v1/beer" + beerDto.getId().toString());
    }

    @Test
    void testUpdateBeer() throws Exception{
        BeerDto beerDto = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("New Beer")
                .build();

        client.updateBeer(UUID.randomUUID(), beerDto);
    }

    @Test
    void testDeleteBeer() throws Exception{
        client.deleteBeer(UUID.randomUUID());
    }
}
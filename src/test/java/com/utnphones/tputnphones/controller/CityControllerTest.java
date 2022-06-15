package com.utnphones.tputnphones.controller;

import com.utnphones.tputnphones.domain.City;
import com.utnphones.tputnphones.services.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getCity;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getCityList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CityControllerTest extends AbstractMVCTest{

    private CityController cityController;

    private CityService cityService;

    @BeforeEach
    public void setUp() {
        cityService= mock(CityService.class);
        cityController = new CityController(cityService);
    }


    @Test
    void findAllTest(){
        when(cityService.findAll()).thenReturn(getCityList());

        ResponseEntity<List<City>> listResponseEntity = cityController.findAll();

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
    }

    @Test
    void getByIdTest(){
        when(cityService.findById(getCity().getIdCity())).thenReturn(getCity());

        ResponseEntity<City>listResponseEntity = cityController.findAllById(getCity().getIdCity());

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
    }

    @Test
    void addCityTest(){
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        when(cityService.save(getCity())).thenReturn(getCity());

        ResponseEntity responseEntity = cityController.newCity(getCity());

        assertEquals(HttpStatus. CREATED.value(),responseEntity.getStatusCodeValue());
    }

}
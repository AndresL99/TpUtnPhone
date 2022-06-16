package com.utnphones.tputnphones.controller;

import com.utnphones.tputnphones.domain.Call;
import com.utnphones.tputnphones.dto.CallDto;
import com.utnphones.tputnphones.services.CallService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.util.List;

import static com.utnphones.tputnphones.utils.TestEntityFactory.getCall;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getCallDto;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getCalls;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CallControllerTest extends AbstractMVCTest{

    private CallController callController;

    private CallService callService;

    @BeforeEach
    public void setUp() {
        callService= mock(CallService.class);
        callController = new CallController(callService);
    }

    @Test
    void findAllTest(){
        when(callService.findAll()).thenReturn(getCalls());

        ResponseEntity<List<Call>> listResponseEntity = callController.findAll();

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
    }

    @Test
    void getByIdTest(){
        when(callService.findById(getCall().getIdCall())).thenReturn(getCall());

        ResponseEntity<Call>listResponseEntity = callController.findAllById(getCall().getIdCall());

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
    }

    @Test
    void getByIdTestBad(){
        when(callService.findById(anyLong())).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> {callController.findAllById(0L);});
    }

    @Test
    void addCallTest() throws ParseException {
        CallDto callDto = getCallDto();
        callService.save(callDto);
        verify(callService, atLeastOnce()).save(callDto);
        ResponseEntity<Call>listResponseEntity = callController.addCall(getCallDto());
        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
    }

    @Test
    void addCallTestBad() throws ParseException {
        when(callService.save(getCallDto())).thenThrow(new EntityNotFoundException("No se encontro el numero"));
        assertThrows(EntityNotFoundException.class, () -> {callController.addCall(getCallDto());});
    }

}
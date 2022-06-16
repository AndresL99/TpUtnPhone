package com.utnphones.tputnphones.controller;

import com.utnphones.tputnphones.domain.Bill;
import com.utnphones.tputnphones.exception.BillNotExistException;
import com.utnphones.tputnphones.services.BillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getBill;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getBillList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BillControllerTest extends AbstractMVCTest{

    private BillController billController;

    private BillService billService;

    @BeforeEach
    public void setUp() {
        billService= mock(BillService.class);
        billController = new BillController(billService);
    }

    @Test
    void findAllTest(){
        when(billService.findAll()).thenReturn(getBillList());

        ResponseEntity<List<Bill>> listResponseEntity = billController.findAll();

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
    }

    @Test
    void getByIdTest(){
        when(billService.findById(getBill().getIdBill())).thenReturn(getBill());

        ResponseEntity<Bill>listResponseEntity = billController.findAllById(getBill().getIdBill());

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
    }

    @Test
    void getByIdTestBad(){
        when(billService.findById(anyLong())).thenThrow(new BillNotExistException("La factura no existe"));
        assertThrows(BillNotExistException.class, () -> {billController.findAllById(0L);});
    }

    @Test
    void generateTest(){
        billService.generateBills();
        verify(billService, atLeastOnce()).generateBills();
        ResponseEntity<Bill>listResponseEntity = billController.generateBills();
        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
    }
}
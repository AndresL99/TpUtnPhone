package com.utnphones.tputnphones.service;

import com.utnphones.tputnphones.domain.Bill;
import com.utnphones.tputnphones.exception.BillExistException;
import com.utnphones.tputnphones.exception.BillNotExistException;
import com.utnphones.tputnphones.repository.BillRepository;
import com.utnphones.tputnphones.services.BillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.utnphones.tputnphones.utils.TestEntityFactory.aPageable;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getBill;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getBillPage;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getBills;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getClient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BillServiceTest {

    private BillRepository billRepository;
    private BillService billService;

    @BeforeEach
    public void setUp()
    {
        billRepository = mock(BillRepository.class);
        billService = new BillService(billRepository);
    }

    @Test
    void saveBillTestOk()
    {
        Long idBill = getBill().getIdBill();
        when(billRepository.existsById(idBill)).thenReturn(false);
        when(billRepository.save(getBill())).thenReturn(getBill());

        Bill bill = billService.save(getBill());

        assertEquals(getBill().getIdBill(),bill.getIdBill());
        verify(billRepository,times(1)).existsById(idBill);
    }

    @Test
    void findAllTest()
    {
        when(billRepository.findAll()).thenReturn(getBills());

        List<Bill>bills = billService.findAll();

        assertNotNull(bills);
        assertEquals(getBills().size(),bills.size());
        verify(billRepository,times(1)).findAll();
    }

    @Test
    void findByIdTestOk()
    {
        Long idBill = getBill().getIdBill();
        when(billRepository.findById(idBill)).thenReturn(Optional.of(getBill()));

        Bill bill = billService.findById(idBill);

        assertNotNull(bill);
        assertSame(getBill().getIdBill(),bill.getIdBill());
        verify(billRepository,times(1)).findById(idBill);
    }

    @Test
    void findByIdTestFail()
    {
        when(billRepository.findById(30L)).thenThrow(BillNotExistException.class);
        assertThrows(BillNotExistException.class,()->billService.findById(30L));
    }

    @Test
    void getBillByClientTest()
    {
        Long idClient = getClient().getIdClient();
        Date start = new Date();
        Date end = new Date();
        when(billRepository.getBillByClient(idClient,start,end,aPageable())).thenReturn(getBillPage());

        Page<Bill>billPage = billService.getBillByClient(idClient,start,end,aPageable());

        assertNotNull(billPage);
        assertEquals(getBillPage().getTotalElements(),billPage.getTotalElements());
        verify(billRepository,times(1)).getBillByClient(idClient,start,end,aPageable());
    }

    @Test
    void generateBillsTest()
    {
        doNothing().when(billRepository).calculateBills();
        billService.generateBills();
        verify(billRepository,times(1)).calculateBills();
    }
}

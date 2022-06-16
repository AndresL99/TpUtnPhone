package com.utnphones.tputnphones.controller.webController;

import com.utnphones.tputnphones.controller.AbstractMVCTest;
import com.utnphones.tputnphones.domain.Bill;
import com.utnphones.tputnphones.domain.Call;
import com.utnphones.tputnphones.dto.UserDto;
import com.utnphones.tputnphones.services.BillService;
import com.utnphones.tputnphones.services.CallService;
import com.utnphones.tputnphones.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.List;

import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.aBackOffice;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getBill;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getBillPage;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getCall;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getCallPage;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getClient;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getPageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClientWebControllerTest extends AbstractMVCTest {

    private ClientWebController clientWebController;

    private BillService billService;

    private CallService callService;

    private ClientService clientService;

    private UserDto backOffice;

    Authentication auth;

    @BeforeEach
    public void setUp() {
        clientService= mock(ClientService.class);
        billService= mock(BillService.class);
        callService= mock(CallService.class);
        backOffice = aBackOffice();
        clientWebController = new ClientWebController(clientService,billService,callService);
        auth= mock(Authentication.class);
    }

    @Test
    void getCallByClientTest(){
        auth = mock(Authentication.class);
        Date start = new Date();
        Date end = new Date();

        when(auth.getPrincipal()).thenReturn(backOffice);
        //when(clientService.getByUsername(((UserDto)auth.getPrincipal()).getUsername())).thenReturn(getClient());
        when(callService.getCallByClient(getClient().getIdClient(),start,end,getPageable())).thenReturn(getCallPage());

        ResponseEntity<List<Call>> responseEntity = clientWebController.getCallByClient(auth,getClient().getIdClient(),start,end,getPageable());
        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());

        assertEquals(getCallPage().getContent().get(0).getIdCall(), responseEntity.getBody().get(0).getIdCall());
        assertEquals(List.of(getCall()),responseEntity.getBody());
    }

    @Test
    void getBillByClient(){
        auth = mock(Authentication.class);
        Date start = new Date();
        Date end = new Date();

        when(auth.getPrincipal()).thenReturn(backOffice);
        when(billService.getBillByClient(getClient().getIdClient(),start,end,getPageable())).thenReturn(getBillPage());

        ResponseEntity<List<Bill>> responseEntity = clientWebController.getBillByClient(auth,getClient().getIdClient(),start,end,getPageable());
        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());

        assertEquals(getBillPage().getContent().get(0).getIdBill(), responseEntity.getBody().get(0).getIdBill());
        assertEquals(List.of(getBill()),responseEntity.getBody());
    }
}
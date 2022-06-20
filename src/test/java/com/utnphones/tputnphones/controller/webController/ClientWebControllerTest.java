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

import static com.utnphones.tputnphones.utils.TestEntityFactory.aBackOffice;
import static com.utnphones.tputnphones.utils.TestEntityFactory.aClient;
import static com.utnphones.tputnphones.utils.TestEntityFactory.aPageable;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getBill;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getBillPage;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getCall;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getCallPage;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getClient;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClientWebControllerTest extends AbstractMVCTest {

    private ClientWebController clientWebController;

    private BillService billService;

    private CallService callService;

    private ClientService clientService;

    private UserDto client;

    Authentication auth;

    @BeforeEach
    public void setUp() {
        clientService= mock(ClientService.class);
        billService= mock(BillService.class);
        callService= mock(CallService.class);
        client = aClient();
        clientWebController = new ClientWebController(clientService,billService,callService);
        auth= mock(Authentication.class);
    }

    @Test
    void getCallByClientTest(){
        auth = mock(Authentication.class);
        Date start = new Date();
        Date end = new Date();

        when(auth.getPrincipal()).thenReturn(client);

        when(callService.getCallByClient(getClient().getIdClient(),start,end,aPageable())).thenReturn(getCallPage());

        ResponseEntity<List<Call>> responseEntity = clientWebController.getCallByClient(auth,getClient().getIdClient(),start,end,aPageable());
        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());

        assertEquals(getCallPage().getContent().get(0).getIdCall(), responseEntity.getBody().get(0).getIdCall());
    }

    @Test
    void getBillByClient(){
        auth = mock(Authentication.class);
        Date start = new Date();
        Date end = new Date();

        when(auth.getPrincipal()).thenReturn(client);
        when(billService.getBillByClient(getClient().getIdClient(),start,end,aPageable())).thenReturn(getBillPage());

        ResponseEntity<List<Bill>> responseEntity = clientWebController.getBillByClient(auth,getClient().getIdClient(),start,end,aPageable());
        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());

        assertEquals(getBillPage().getContent().get(0).getIdBill(), responseEntity.getBody().get(0).getIdBill());
    }
}

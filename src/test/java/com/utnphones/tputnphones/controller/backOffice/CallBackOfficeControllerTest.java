package com.utnphones.tputnphones.controller.backOffice;

import com.utnphones.tputnphones.controller.AbstractMVCTest;
import com.utnphones.tputnphones.domain.Call;
import com.utnphones.tputnphones.dto.UserDto;
import com.utnphones.tputnphones.services.CallService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Date;
import java.util.List;

import static com.utnphones.tputnphones.utils.TestEntityFactory.aBackOffice;
import static com.utnphones.tputnphones.utils.TestEntityFactory.aPageable;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getCall;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getCallPage;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CallBackOfficeControllerTest extends AbstractMVCTest {

    private CallBackOfficeController callBackOfficeController;

    private CallService callService;

    private UserDto backOffice;

    Authentication auth;

    @BeforeEach
    public void setUp() {
        callService= mock(CallService.class);
        backOffice = aBackOffice();
        callBackOfficeController = new CallBackOfficeController(callService);
        auth= mock(Authentication.class);
    }

    @Test
    void getCallByUserTest(){
    auth = mock(Authentication.class);
    Date start = new Date();
    Date end = new Date();
    Integer dni = getUser().getDni();

    when(auth.getPrincipal()).thenReturn(backOffice);

    when(callService.getCallByUserAndRank(dni,start,end,aPageable())).thenReturn(getCallPage());

    ResponseEntity<List<Call>> responseEntity = callBackOfficeController.getCallByUser(auth,dni,start,end,aPageable());
    assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());

    assertEquals(getCallPage().getContent().get(0).getIdCall(), responseEntity.getBody().get(0).getIdCall());
    }

}
package com.utnphones.tputnphones.controller.backOffice;

import com.utnphones.tputnphones.controller.AbstractMVCTest;
import com.utnphones.tputnphones.domain.Client;
import com.utnphones.tputnphones.dto.UserDto;
import com.utnphones.tputnphones.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;

import static com.utnphones.tputnphones.utils.TestEntityFactory.aBackOffice;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getClient;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getClients;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClientBackOfficeControllerTest extends AbstractMVCTest {

    private ClientBackOfficeController clientBackOfficeController;

    private ClientService clientService;

    private UserDto backOffice;

    Authentication auth;

    @BeforeEach
    public void setUp() {
        clientService= mock(ClientService.class);
        backOffice = aBackOffice();
        clientBackOfficeController = new ClientBackOfficeController(clientService);
        auth= mock(Authentication.class);
    }

    @Test
    void findAllTest(){
        auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(backOffice);

        when(clientService.getAllClient()).thenReturn(getClients());

        ResponseEntity<List<Client>> listResponseEntity = clientBackOfficeController.getAllClient(auth);

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
    }

    @Test
    void findAllNoContentTest(){
        auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(backOffice);

        when(clientService.getAllClient()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Client>> listResponseEntity = clientBackOfficeController.getAllClient(auth);

        assertEquals(HttpStatus.NO_CONTENT,listResponseEntity.getStatusCode());
    }


    @Test
    void findClientTest(){
        auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(backOffice);

        when(clientService.getById(getClient().getIdClient())).thenReturn(getClient());

        ResponseEntity<Client> listResponseEntity = clientBackOfficeController.getClient(auth,getClient().getIdClient());

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());

    }

    @Test
    void deleteClientTest(){
        auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(backOffice);

        clientService.deleteById(getClient().getIdClient());
        verify(clientService,atLeastOnce()).deleteById(getClient().getIdClient());

        ResponseEntity<Client> listResponseEntity = clientBackOfficeController.deleteClient(auth,getClient().getIdClient());

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());

    }


    @Test
    void newPhoneLine(){
        auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(backOffice);

        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        when(clientService.addClient(getClient())).thenReturn(getClient());

        ResponseEntity responseEntity = clientBackOfficeController.addClient(auth,getClient());

        assertEquals(HttpStatus. CREATED.value(),responseEntity.getStatusCodeValue());
    }

}

package com.utnphones.tputnphones.service;

import com.utnphones.tputnphones.domain.Client;
import com.utnphones.tputnphones.exception.ClientExistException;
import com.utnphones.tputnphones.exception.ClientNotExistException;
import com.utnphones.tputnphones.repository.ClientRepository;
import com.utnphones.tputnphones.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.utnphones.tputnphones.utils.TestEntityFactory.getClient;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getClients;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClientServiceTest {

    private ClientRepository clientRepository;
    private ClientService clientService;

    @BeforeEach
    public void setUp()
    {
        clientRepository = mock(ClientRepository.class);
        clientService = new ClientService(clientRepository);
    }

    @Test
    void addClientTestOk()
    {
        Long idClient = getClient().getIdClient();
        when(clientRepository.save(getClient())).thenReturn(getClient());

        Client client = clientService.addClient(getClient());

        assertEquals(getClient(),client);
    }

    @Test
    void addClientTestFail()
    {
        when(clientRepository.save(getClient())).thenThrow(ClientExistException.class);
        assertThrows(ClientExistException.class,()->clientService.addClient(getClient()));
    }

    @Test
    void findByIdTestOk()
    {
        Long idClient = getClient().getIdClient();
        when(clientRepository.findById(idClient)).thenReturn(Optional.of(getClient()));

        Client client = clientService.getById(idClient);

        assertEquals(getClient(),client);
        verify(clientRepository,times(1)).findById(idClient);
    }

    @Test
    void findByTestFail()
    {
        when(clientRepository.findById(4L)).thenThrow(ClientNotExistException.class);
        assertThrows(ClientNotExistException.class,()->clientService.getById(4L));
    }

    @Test
    void getAllTest()
    {
        when(clientRepository.findAll()).thenReturn(getClients());

        List<Client>clients = clientService.getAllClient();

        assertEquals(getClients(),clients);
        verify(clientRepository,times(1)).findAll();
    }

    @Test
    void deleteTest()
    {
        doNothing().when(clientRepository).deleteById(getClient().getIdClient());

        clientService.deleteById(getClient().getIdClient());

        verify(clientRepository,times(1)).deleteById(getClient().getIdClient());
    }

    @Test
    void getByUsernameTest()
    {
        String username = getClient().getUser().getUsername();
        when(clientRepository.getClientByUsername(username)).thenReturn(getClient());

        Client client = clientService.getByUsername(username);

        assertEquals(getClient(),client);
        verify(clientRepository,times(1)).getClientByUsername(username);
    }
}

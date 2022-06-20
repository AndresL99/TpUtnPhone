package com.utnphones.tputnphones.services;

import com.utnphones.tputnphones.domain.Client;
import com.utnphones.tputnphones.exception.ClientExistException;
import com.utnphones.tputnphones.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class ClientService
{

    private ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client addClient(Client client)
    {
        if(client.getIdClient() != null)
        {
            return clientRepository.save(client);
        }
        else
        {
            throw new ClientExistException("El client ya existe.");
        }
    }

    public Client getById(Long id)
    {
        return clientRepository.findById(id).orElseThrow(()-> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public List<Client>getAllClient()
    {
        return clientRepository.findAll();
    }

    public void deleteById(Long id)
    {
        clientRepository.deleteById(id);
    }

    public Client getByUsername(String username)
    {
        return clientRepository.getClientByUsername(username);
    }




}

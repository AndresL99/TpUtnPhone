package com.utnphones.tputnphones.controller.backOffice;

import com.utnphones.tputnphones.domain.Bill;
import com.utnphones.tputnphones.domain.Call;
import com.utnphones.tputnphones.domain.Client;
import com.utnphones.tputnphones.dto.UserDto;
import com.utnphones.tputnphones.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/backOffice/client")
public class ClientBackOfficeController
{
    private ClientService clientService;

    @Autowired
    public ClientBackOfficeController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity addClient(Authentication authentication,@RequestBody Client client,HttpServletRequest request)
    {
        verifyAuthBackOffice(authentication);
        Client newClient = this.clientService.addClient(client);
        URI location = ServletUriComponentsBuilder.
                fromServletMapping(request)
                .path("/backOffice/client/" + newClient.getIdClient()).build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{idClient}")
    public ResponseEntity<Client> getClient(Authentication authentication,Long idClient)
    {
        verifyAuthBackOffice(authentication);
        Client client = this.clientService.getById(idClient);
        return ResponseEntity.ok(client);
    }

    @GetMapping
    public ResponseEntity<List<Client>>getAllClient(Authentication authentication)
    {
        verifyAuthBackOffice(authentication);
        List<Client>clients = this.clientService.getAllClient();
        return clients.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(clients);
    }

    @DeleteMapping("/{idClient}")
    public ResponseEntity deleteClient(Authentication authentication,@PathVariable Long idClient)
    {
        verifyAuthBackOffice(authentication);
        clientService.deleteById(idClient);
        return new ResponseEntity(HttpStatus.OK);
    }


    private void verifyAuthBackOffice(Authentication authentication)
    {
        if(!((UserDto) authentication.getPrincipal()).getEmployee())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access forbidden for your profile.");
    }
}

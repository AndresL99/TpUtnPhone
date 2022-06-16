package com.utnphones.tputnphones.controller.webController;

import com.utnphones.tputnphones.domain.Bill;
import com.utnphones.tputnphones.domain.Call;
import com.utnphones.tputnphones.domain.Client;
import com.utnphones.tputnphones.dto.UserDto;
import com.utnphones.tputnphones.services.BillService;
import com.utnphones.tputnphones.services.CallService;
import com.utnphones.tputnphones.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/Client")
public class ClientWebController
{

    private ClientService clientService;
    private BillService billService;
    private CallService callService;

    @Autowired
    public ClientWebController(ClientService clientService, BillService billService, CallService callService) {
        this.clientService = clientService;
        this.billService = billService;
        this.callService = callService;
    }


    // Consulta de llamadas del usuario logueado por rango de fechas.
    @GetMapping("/{idClient}/calls")
    public ResponseEntity<List<Call>>getCallByClient(Authentication authentication,
                                                  @PathVariable Long idClient,
                                                  @RequestParam ("start") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date start,
                                                  @RequestParam ("end") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date end,
                                                  Pageable pageable)
    {
        verifyAuthBackOffice(authentication);
        Page<Call>calls = callService.getCallByClient(idClient,start,end,pageable);
        return response(calls);
    }


    //Consulta de facturas del usuario logueado por rango de fechas
    @GetMapping("/{idClient}/bills")
    public ResponseEntity<List<Bill>>getBillByClient(Authentication authentication,
                                                    @PathVariable Long idClient,
                                                    @RequestParam ("start") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date start,
                                                    @RequestParam ("end") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date end,
                                                    Pageable pageable)
    {
        verifyAuthBackOffice(authentication);
        Page<Bill>bills = billService.getBillByClient(idClient,start,end,pageable);
        return response(bills);
    }

    private void verifyAuthClient(Authentication authentication, Long idClient)
    {
        Client client = this.clientService.getByUsername(((UserDto)authentication.getPrincipal()).getUsername());
        if(!client.getIdClient().equals(idClient))
        {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Denied Access");
        }
    }

    private void verifyAuthBackOffice(Authentication authentication)
    {
        if(!((UserDto) authentication.getPrincipal()).getEmployee()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access forbidden for your profile.");
        }

    }


    private ResponseEntity response(List list, Page page) {
        HttpStatus status = !list.isEmpty() ? HttpStatus.OK : HttpStatus.NO_CONTENT;
        return ResponseEntity.status(status).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());
    }


    private ResponseEntity response(List list) {
        return ResponseEntity.status(list.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(list);
    }

    private ResponseEntity response(Page page) {
        HttpStatus httpStatus = page.getContent().isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity.
                status(httpStatus).
                header("X-Total-Count", Long.toString(page.getTotalElements())).
                header("X-Total-Pages", Long.toString(page.getTotalPages())).
                body(page.getContent());

    }
}

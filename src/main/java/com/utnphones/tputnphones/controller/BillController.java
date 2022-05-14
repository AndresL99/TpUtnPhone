package com.utnphones.tputnphones.controller;

import com.utnphones.tputnphones.domain.Bill;
import com.utnphones.tputnphones.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RequestMapping(value = "/api/bill")
@RestController
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping
    public ResponseEntity newBill(@RequestBody Bill bill, HttpServletRequest request){
        Bill newBill = billService.save(bill);
        URI location = ServletUriComponentsBuilder.
                fromServletMapping(request)
                .path("/api/bill/" + newBill.getIdBill()).build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<Bill>> findAll(){
        return ResponseEntity.ok(billService.findAll());
    }

    @GetMapping(value = "/{billId}")
    public ResponseEntity<Bill> findAllById(@PathVariable Long id){
        return ResponseEntity.ok(billService.findById(id));
    }

}

package com.utnphones.tputnphones.controller;

import com.utnphones.tputnphones.domain.Bill;
import com.utnphones.tputnphones.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RequestMapping(value = "/api/bill")
@RestController
public class BillController {

    private BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping
    public ResponseEntity<List<Bill>> findAll(){
        return ResponseEntity.ok(billService.findAll());
    }

    @GetMapping(value = "/{billId}")
    public ResponseEntity<Bill> findAllById(@PathVariable Long id){
        return ResponseEntity.ok(billService.findById(id));
    }

    @PostMapping("/generate")
    public ResponseEntity generateBills(){
        billService.generateBills();
        return new ResponseEntity(HttpStatus.OK);
    }
}

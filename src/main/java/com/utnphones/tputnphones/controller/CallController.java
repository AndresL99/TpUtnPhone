package com.utnphones.tputnphones.controller;

import com.utnphones.tputnphones.domain.Call;
import com.utnphones.tputnphones.dto.CallDto;
import com.utnphones.tputnphones.services.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

import java.util.List;

@RequestMapping(value = "/api/calls")
@RestController
public class CallController {

    @Autowired
    private CallService callService;

    @GetMapping
    public ResponseEntity<List<Call>> findAll(){
        return ResponseEntity.ok(callService.findAll());
    }

    @GetMapping(value = "/{callId}")
    public ResponseEntity<Call> findAllById(@PathVariable Long id){
        return ResponseEntity.ok(callService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Call> addCall(@RequestBody CallDto incoming) throws ParseException {
        System.out.println("INCOMING READING: " + incoming.toString());

        callService.save(incoming);
        return ResponseEntity.ok(Call.builder().build());
    }

}

package com.utnphones.tputnphones.controller;

import com.utnphones.tputnphones.domain.phoneLine;
import com.utnphones.tputnphones.services.PhoneLineService;
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

@RequestMapping(value = "/api/phoneLine")
@RestController
public class PhoneLineController {

    @Autowired
    private PhoneLineService phoneLineService;

    @PostMapping
    public ResponseEntity newPhoneLine(@RequestBody phoneLine phoneLine, HttpServletRequest request){
        phoneLine newPhoneLine = phoneLineService.save(phoneLine);
        URI location = ServletUriComponentsBuilder.
                fromServletMapping(request)
                .path("/api/phoneLine/" + newPhoneLine.getPhoneNumber()).build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<phoneLine>> findAll(){
        return ResponseEntity.ok(phoneLineService.findAll());
    }

    /*
    @GetMapping(value = "/{phoneLineId}")
    public ResponseEntity<phoneLine> findAllById(@PathVariable Integer id){
        return ResponseEntity.ok(phoneLineService.findById(id));
    }*/
}

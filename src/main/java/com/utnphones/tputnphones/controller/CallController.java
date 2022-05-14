package com.utnphones.tputnphones.controller;

import com.utnphones.tputnphones.domain.Call;
import com.utnphones.tputnphones.services.CallService;
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

@RequestMapping(value = "/api/call")
@RestController
public class CallController {

    @Autowired
    private CallService callService;

    @PostMapping
    public ResponseEntity newCall(@RequestBody Call call, HttpServletRequest request){
        Call newCall = callService.save(call);
        URI location = ServletUriComponentsBuilder.
                fromServletMapping(request)
                .path("/api/call/" + newCall.getIdCall()).build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<Call>> findAll(){
        return ResponseEntity.ok(callService.findAll());
    }

    @GetMapping(value = "/{callId}")
    public ResponseEntity<Call> findAllById(@PathVariable Long id){
        return ResponseEntity.ok(callService.findById(id));
    }
}

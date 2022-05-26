package com.utnphones.tputnphones.controller;

import com.utnphones.tputnphones.domain.Tariff;
import com.utnphones.tputnphones.services.TariffService;
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

@RequestMapping(value = "/api/tariff")
@RestController
public class TariffController {

    @Autowired
    private TariffService tariffService;

    @PostMapping
    public ResponseEntity newPhoneLine(@RequestBody Tariff tariff, HttpServletRequest request){
        Tariff newTariff = tariffService.save(tariff);
        URI location = ServletUriComponentsBuilder.
                fromServletMapping(request)
                .path("/api/phoneLine/" + newTariff.getIdTariff()).build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<Tariff>> findAll(){
        return ResponseEntity.ok(tariffService.findAll());
    }

    @GetMapping(value = "/{tariffId}")
    public ResponseEntity<Tariff> findAllById(@PathVariable Long id){
        return ResponseEntity.ok(tariffService.findById(id));
    }

    @GetMapping(value = "/{cityOriginId}/{cityDestinationId}")
    public ResponseEntity<Long> findByIds(@PathVariable Long cityOriginId,@PathVariable Long cityDestinationId){
        return ResponseEntity.ok(tariffService.findByCities(cityOriginId,cityDestinationId));
    }
}

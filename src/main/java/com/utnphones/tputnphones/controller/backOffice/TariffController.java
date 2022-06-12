package com.utnphones.tputnphones.controller.backOffice;

import com.utnphones.tputnphones.domain.Client;
import com.utnphones.tputnphones.domain.Tariff;
import com.utnphones.tputnphones.dto.UserDto;
import com.utnphones.tputnphones.services.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RequestMapping(value = "/backOffice/tariff")
@RestController
public class TariffController {


    private TariffService tariffService;

    @Autowired
    public TariffController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @PostMapping
    public ResponseEntity addTariff(Authentication authentication,@RequestBody Tariff tariff, HttpServletRequest request){

        verifyAuthBackOffice(authentication);
        Tariff newTariff = this.tariffService.save(tariff);
        URI location = ServletUriComponentsBuilder.
                fromServletMapping(request)
                .path("/backOffice/tariff/" + newTariff.getIdTariff()).build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<Tariff>> findAll(Authentication authentication){
        verifyAuthBackOffice(authentication);
        List<Tariff>tariffs = this.tariffService.findAll();
        return tariffs.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(tariffs);
    }

    @GetMapping(value = "/{tariffId}")
    public ResponseEntity<Tariff> findAllById(Authentication authentication,@PathVariable Long id){
        verifyAuthBackOffice(authentication);
        return ResponseEntity.ok(tariffService.findById(id));
    }

    @GetMapping(value = "/{cityOriginId}/{cityDestinationId}")
    public ResponseEntity<Long> findByIds(Authentication authentication,@PathVariable Long cityOriginId,@PathVariable Long cityDestinationId){
        verifyAuthBackOffice(authentication);
        return ResponseEntity.ok(tariffService.findByCities(cityOriginId,cityDestinationId));
    }


    private void verifyAuthBackOffice(Authentication authentication)
    {
        if(!((UserDto) authentication.getPrincipal()).getEmployee())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access forbidden for your profile.");
    }
}

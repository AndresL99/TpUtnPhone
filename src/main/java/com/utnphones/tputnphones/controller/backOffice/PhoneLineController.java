package com.utnphones.tputnphones.controller.backOffice;

import com.utnphones.tputnphones.domain.PhoneLine;
import com.utnphones.tputnphones.dto.UserDto;
import com.utnphones.tputnphones.services.PhoneLineService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RequestMapping(value = "/backOffice/phoneLine")
@RestController
public class PhoneLineController {


    private PhoneLineService phoneLineService;

    @Autowired
    public PhoneLineController(PhoneLineService phoneLineService) {
        this.phoneLineService = phoneLineService;
    }

    @PostMapping
    public ResponseEntity newPhoneLine(Authentication authentication, @RequestBody PhoneLine phoneLine, HttpServletRequest request){

        verifyAuthBackOffice(authentication);
        PhoneLine newPhoneLine = phoneLineService.save(phoneLine);
        URI location = ServletUriComponentsBuilder.
                fromServletMapping(request)
                .path("/backOffice/phoneLine/" + newPhoneLine.getPhoneNumber()).build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<PhoneLine>> findAll(Authentication authentication)
    {
        verifyAuthBackOffice(authentication);
        List<PhoneLine>phoneLines = this.phoneLineService.findAll();
        return phoneLines.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(phoneLines);
    }

    @DeleteMapping("/{phoneNumber}")
    public ResponseEntity deletePhoneLine(Authentication authentication, @PathVariable String phoneNumber)
    {
        this.phoneLineService.deletePhoneLine(phoneNumber);
        return new ResponseEntity(HttpStatus.OK);
    }

    private void verifyAuthBackOffice(Authentication authentication)
    {
        if(!((UserDto) authentication.getPrincipal()).getEmployee())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access forbidden for your profile.");
    }
}

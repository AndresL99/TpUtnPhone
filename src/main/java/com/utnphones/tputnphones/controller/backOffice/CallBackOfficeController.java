package com.utnphones.tputnphones.controller.backOffice;

import com.utnphones.tputnphones.domain.Call;
import com.utnphones.tputnphones.dto.UserDto;
import com.utnphones.tputnphones.services.CallService;
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
@RequestMapping("/backOffice/calls")
public class CallBackOfficeController
{
    private CallService callService;

    @Autowired
    public CallBackOfficeController(CallService callService) {
        this.callService = callService;
    }

    @GetMapping("/{dni}/users")
    public ResponseEntity<List<Call>>getCallByUser(Authentication authentication,
                                                   @PathVariable Integer dni,
                                                   @RequestParam("start") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date start,
                                                   @RequestParam ("end") @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date end,
                                                   Pageable pageable)
    {
        verifyAuthBackOffice(authentication);
        Page<Call>calls = this.callService.getCallByUserAndRank(dni,start,end,pageable);
        return response(calls);
    }

    private void verifyAuthBackOffice(Authentication authentication)
    {
        if(!((UserDto) authentication.getPrincipal()).getEmployee())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access forbidden for your profile.");
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

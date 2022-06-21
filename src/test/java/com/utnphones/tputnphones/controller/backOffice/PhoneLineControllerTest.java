package com.utnphones.tputnphones.controller.backOffice;

import com.utnphones.tputnphones.controller.AbstractMVCTest;
import com.utnphones.tputnphones.domain.PhoneLine;
import com.utnphones.tputnphones.dto.UserDto;
import com.utnphones.tputnphones.services.PhoneLineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collections;
import java.util.List;

import static com.utnphones.tputnphones.utils.TestEntityFactory.aBackOffice;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getPhoneLines;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getTelephoneLine;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PhoneLineControllerTest extends AbstractMVCTest {

    private PhoneLineController phoneLineController;

    private PhoneLineService phoneLineService;

    private UserDto backOffice;

    Authentication auth;

    @BeforeEach
    public void setUp() {
        phoneLineService= mock(PhoneLineService.class);
        backOffice = aBackOffice();
        phoneLineController = new PhoneLineController(phoneLineService);
        auth= mock(Authentication.class);
    }

    @Test
    void findAllTest(){
        auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(backOffice);

        when(phoneLineService.findAll()).thenReturn(getPhoneLines());

        ResponseEntity<List<PhoneLine>> listResponseEntity = phoneLineController.findAll(auth);

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
    }

    @Test
    void findAllNoContentTest(){
        auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(backOffice);

        when(phoneLineService.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<List<PhoneLine>> listResponseEntity = phoneLineController.findAll(auth);

        assertEquals(HttpStatus.NO_CONTENT,listResponseEntity.getStatusCode());
    }


    @Test
    void deletePhoneLineTest(){
        auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(backOffice);

        phoneLineService.deletePhoneLine(getTelephoneLine().getPhoneNumber());
        verify(phoneLineService,atLeastOnce()).deletePhoneLine(getTelephoneLine().getPhoneNumber());

        ResponseEntity<PhoneLine> listResponseEntity = phoneLineController.deletePhoneLine(auth,getTelephoneLine().getPhoneNumber());

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());

    }

    @Test
    void newPhoneLineTest(){
        auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(backOffice);

        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        when(phoneLineService.save(getTelephoneLine())).thenReturn(getTelephoneLine());

        ResponseEntity responseEntity = phoneLineController.newPhoneLine(auth,getTelephoneLine());

        assertEquals(HttpStatus. CREATED.value(),responseEntity.getStatusCodeValue());
    }

    @Test
    void getPhoneLineTest(){
        auth = mock(Authentication.class);
        when(auth.getPrincipal()).thenReturn(backOffice);

        when(phoneLineService.getByPhoneNumber(getTelephoneLine().getPhoneNumber())).thenReturn(getTelephoneLine());
        ResponseEntity responseEntity = phoneLineController.getPhoneLine(auth,getTelephoneLine().getPhoneNumber());

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
    }
}

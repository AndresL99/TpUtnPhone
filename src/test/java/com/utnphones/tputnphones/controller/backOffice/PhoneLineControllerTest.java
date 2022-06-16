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

import java.util.List;

import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.aBackOffice;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getPhoneLine;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getPhoneLineList;
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

        when(phoneLineService.findAll()).thenReturn(getPhoneLineList());

        ResponseEntity<List<PhoneLine>> listResponseEntity = phoneLineController.findAll(auth);

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
    }

    @Test
    void deletePhoneLineTest(){
        auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(backOffice);

        phoneLineService.deletePhoneLine(getPhoneLine().getPhoneNumber());
        verify(phoneLineService,atLeastOnce()).deletePhoneLine(getPhoneLine().getPhoneNumber());

        ResponseEntity<PhoneLine> listResponseEntity = phoneLineController.deletePhoneLine(auth,getPhoneLine().getPhoneNumber());

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());

    }

    @Test
    void newPhoneLine(){
        auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(backOffice);

        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        when(phoneLineService.save(getPhoneLine())).thenReturn(getPhoneLine());

        ResponseEntity responseEntity = phoneLineController.newPhoneLine(auth,getPhoneLine());

        assertEquals(HttpStatus. CREATED.value(),responseEntity.getStatusCodeValue());
    }

}

package com.utnphones.tputnphones.controller.backOffice;

import com.utnphones.tputnphones.controller.AbstractMVCTest;
import com.utnphones.tputnphones.domain.Tariff;
import com.utnphones.tputnphones.dto.UserDto;
import com.utnphones.tputnphones.exception.BillNotExistException;
import com.utnphones.tputnphones.exception.TariffExistException;
import com.utnphones.tputnphones.exception.UserExistException;
import com.utnphones.tputnphones.services.TariffService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityNotFoundException;
import java.text.ParseException;
import java.util.List;

import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.aBackOffice;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getCallDto;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getTariff;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getTariffList;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TariffControllerTest extends AbstractMVCTest {

    private TariffController tariffController;

    private TariffService tariffService;

    private UserDto backOffice;

    Authentication auth;

    @BeforeEach
    public void setUp() {
        tariffService= mock(TariffService.class);
        backOffice = aBackOffice();
        tariffController = new TariffController(tariffService);
        auth= mock(Authentication.class);
    }

    @Test
    void findByIdsTest(){

        auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(backOffice);
        when(tariffService.findByCities(getTariff().getOriginCity().getIdCity(),getTariff().getDestinationCity().getIdCity())).thenReturn(1L);

        ResponseEntity<Long> listResponseEntity = tariffController.findByIds(auth,getTariff().getOriginCity().getIdCity(),getTariff().getDestinationCity().getIdCity());

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
    }

    @Test
    void findAllById(){

        auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(backOffice);

        when(tariffService.findById(getTariff().getIdTariff())).thenReturn(getTariff());

        ResponseEntity<Tariff> listResponseEntity = tariffController.findAllById(auth,getTariff().getIdTariff());

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
    }

    @Test
    void findAllByIdBad(){
        when(tariffService.findById(anyLong())).thenThrow(new EntityNotFoundException("La tarifa no existe"));
        assertThrows(EntityNotFoundException.class, () -> {tariffController.findAllById(auth,0L);});
    }

    @Test
    void findAll(){

        auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(backOffice);

        when(tariffService.findAll()).thenReturn(getTariffList());

        ResponseEntity<List<Tariff>> listResponseEntity = tariffController.findAll(auth);

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
    }

    @Test
    void addTariffTest(){

        auth = mock(Authentication.class);

        when(auth.getPrincipal()).thenReturn(backOffice);

        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        when(tariffService.save(getTariff())).thenReturn(getTariff());

        ResponseEntity responseEntity = tariffController.addTariff(auth,getTariff());

        assertEquals(HttpStatus. CREATED.value(),responseEntity.getStatusCodeValue());
    }

}

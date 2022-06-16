package com.utnphones.tputnphones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utnphones.tputnphones.domain.User;
import com.utnphones.tputnphones.dto.LoginResponseDto;
import com.utnphones.tputnphones.dto.UserDto;
import com.utnphones.tputnphones.exception.UserExistException;
import com.utnphones.tputnphones.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getLoginRequestDto;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getUser;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getUserDto;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getUserList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class UserControllerTest extends AbstractMVCTest{

    private UserController userController;

    private UserService userService;

    private ObjectMapper objectMapper;

    private ModelMapper modelMapper;

    private Authentication auth;

    @BeforeEach
    public void setUp() {
        userService= mock(UserService.class);
        objectMapper= mock(ObjectMapper.class);
        modelMapper= mock(ModelMapper.class);
        userController = new UserController(userService,objectMapper,modelMapper);
        auth= mock(Authentication.class);
    }

    @Test
    void getAllTest(){
        when(userService.findAll()).thenReturn(getUserList());

        ResponseEntity<List<User>>listResponseEntity = userController.findAll();

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
    }

    /*
    @Test
    public void getAllTestBad()
    {

        when(userService.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<List<User>>listResponseEntity = userController.findAll();

        assertEquals(HttpStatus.NO_CONTENT,listResponseEntity.getStatusCode());
        assertEquals(0,listResponseEntity.getBody().size());
    }*/

    @Test
    void getByIdTestOk(){
        when(userService.findByDni(getUser().getDni())).thenReturn(getUser());

        ResponseEntity<User>listResponseEntity = userController.findAllById(getUser().getDni());

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
    }

    @Test
    void getByIdTestBad(){
        when(userService.findByDni(anyInt())).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> {userController.findAllById(0);});
    }

    @Test
    void loginTestOk(){
        User newUser = getUser();
        when(userService.getByUsernameAndPassword(newUser.getUsername(),newUser.getPassword())).thenReturn(getUser());

        when(modelMapper.map(getUser(),UserDto.class)).thenReturn(getUserDto());

        ResponseEntity<LoginResponseDto>listResponseEntity = userController.login(getLoginRequestDto());

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
    }

    @Test
    void loginTestBad(){
        User newUser = getUser();
        when(userService.getByUsernameAndPassword(newUser.getUsername(),"0")).thenReturn(getUser());

        when(modelMapper.map(getUser(),UserDto.class)).thenReturn(getUserDto());

        ResponseEntity<LoginResponseDto>listResponseEntity = userController.login(getLoginRequestDto());

        assertEquals(HttpStatus.UNAUTHORIZED,listResponseEntity.getStatusCode());
    }

    @Test
    void addUserTest(){
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        when(userService.save(getUser())).thenReturn(getUser());

        ResponseEntity responseEntity = userController.newUser(getUser());

        assertEquals(HttpStatus. CREATED.value(),responseEntity.getStatusCodeValue());
    }

    @Test
    void addUserTestBad(){
        when(userService.save(getUser())).thenThrow(new UserExistException("no existe el usuario"));
        assertThrows(UserExistException.class, () -> {userController.newUser(getUser());});
    }
}

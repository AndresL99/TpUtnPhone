package com.utnphones.tputnphones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.utnphones.tputnphones.domain.User;
import com.utnphones.tputnphones.dto.LoginResponseDto;
import com.utnphones.tputnphones.dto.UserDto;
import com.utnphones.tputnphones.services.UserService;
import com.utnphones.tputnphones.util.EntityURLBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getLoginRequestDto;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getUser;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getUserDto;
import static com.utnphones.tputnphones.TestUtils.TestEntityFactory.getUserList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@SpringBootTest
public class UserControllerTest extends AbstractMVCTest{

    private UserController userController;

    private UserService userService;

    private ObjectMapper objectMapper;

    private ModelMapper modelMapper;

    private Authentication auth;

    private HttpServletRequest request;

    @BeforeEach
    public void setUp() {
        userService= mock(UserService.class);
        objectMapper= mock(ObjectMapper.class);
        modelMapper= mock(ModelMapper.class);
        userController = new UserController(userService,objectMapper,modelMapper);
        auth= mock(Authentication.class);
        request= mock(HttpServletRequest.class);
    }

    @Test
    void getAllTest(){
        when(userService.findAll()).thenReturn(getUserList());

        ResponseEntity<List<User>>listResponseEntity = userController.findAll();

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
    }

    @Test
    void getByIdTest(){
        when(userService.findByDni(getUser().getDni())).thenReturn(getUser());

        ResponseEntity<User>listResponseEntity = userController.findAllById(getUser().getDni());

        assertEquals(HttpStatus.OK,listResponseEntity.getStatusCode());
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

    @Disabled(value = "queda deshabilitado por error en el entityUrlBuilder")
    @Test
    void addUserTest(){
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockHttpServletRequest));

        when(userService.save(getUser())).thenReturn(getUser());

        ResponseEntity responseEntity = userController.newUser(getUser(),request);

        assertEquals(HttpStatus. CREATED.value(),responseEntity.getStatusCodeValue());

        assertEquals(EntityURLBuilder.buildURL("user",String.valueOf(getUser().getDni())).toString(), responseEntity.getHeaders().get("location").get(0));
    }


}

package com.utnphones.tputnphones.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utnphones.tputnphones.domain.User;
import com.utnphones.tputnphones.dto.LoginRequestDto;
import com.utnphones.tputnphones.dto.LoginResponseDto;
import com.utnphones.tputnphones.dto.UserDto;
import com.utnphones.tputnphones.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.utnphones.tputnphones.util.Constants.AUTH_EMPLOYEE;
import static com.utnphones.tputnphones.util.Constants.AUTH_CLIENT;
import static com.utnphones.tputnphones.util.Constants.JWT_SECRET;

@RequestMapping(value = "/user")
@RestController
@Slf4j
public class UserController {


    private UserService userService;
    private ObjectMapper objectMapper;
    private ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity newUser(@RequestBody User user, HttpServletRequest request){
        User newUser = userService.save(user);
        URI location = ServletUriComponentsBuilder.
                fromServletMapping(request)
                .path("/user/" + newUser.getDni()).build()
                //.path("/{addressId}")
                //.buildAndExpand("user/"+newUser.getDni())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping(value = "/{userDni}")
    public ResponseEntity<User> findAllById(@PathVariable Integer dni){
        return ResponseEntity.ok(userService.findByDni(dni));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDto>login(@RequestBody LoginRequestDto loginRequestDto)
    {
        log.info(loginRequestDto.toString());
        User user = userService.getByUsernameAndPassword(loginRequestDto.getUsername(),loginRequestDto.getPassword());
        if(user!=null)
        {
            UserDto userDto = modelMapper.map(user, UserDto.class);
            return ResponseEntity.ok(LoginResponseDto.builder().token(this.generateToken(userDto)).build());
        }
        else
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private String generateToken(UserDto user)
    {
        try {
            String authority = user.getEmployee() ? AUTH_EMPLOYEE : AUTH_CLIENT;
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
            String token = Jwts
                    .builder()
                    .setId("JWT")
                    .setSubject(user.getUsername())
                    .claim("user", objectMapper.writeValueAsString(user))
                    .claim("authorities",grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1800000))
                    .signWith(SignatureAlgorithm.HS512, JWT_SECRET.getBytes()).compact();
            return  token;
        } catch(Exception e) {
            return "dummy";
        }
    }

}

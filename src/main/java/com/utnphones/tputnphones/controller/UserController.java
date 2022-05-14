package com.utnphones.tputnphones.controller;

import com.utnphones.tputnphones.domain.User;
import com.utnphones.tputnphones.services.UserService;
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

@RequestMapping(value = "/api/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity newUser(@RequestBody User user, HttpServletRequest request){
        User newUser = userService.save(user);
        URI location = ServletUriComponentsBuilder.
                fromServletMapping(request)
                .path("/api/phoneLine/" + newUser.getDni()).build()
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
}

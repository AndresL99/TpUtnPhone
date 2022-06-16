package com.utnphones.tputnphones.service;

import com.utnphones.tputnphones.domain.User;
import com.utnphones.tputnphones.exception.UserExistException;
import com.utnphones.tputnphones.repository.UserRepository;
import com.utnphones.tputnphones.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static com.utnphones.tputnphones.utils.TestEntityFactory.getUser;
import static com.utnphones.tputnphones.utils.TestEntityFactory.getUsers;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest
{
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setUp()
    {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void addUserTestOk()
    {
        Integer idUser = getUser().getDni();
        when(userRepository.existsById(idUser)).thenReturn(false);
        when(userRepository.save(getUser())).thenReturn(getUser());

        User user = userService.save(getUser());

        assertEquals(getUser(),user);
    }

    @Test
    void addUserTestFailed()
    {
        when(userRepository.existsById(44222444)).thenReturn(true);
        when(userRepository.save(getUser())).thenThrow(UserExistException.class);
        assertThrows(UserExistException.class,()->userService.save(getUser()));
    }

    @Test
    void findAllUserTest()
    {
        when(userRepository.findAll()).thenReturn(getUsers());

        List<User>users = userService.findAll();

        assertEquals(getUsers().size(),users.size());
        verify(userRepository,times(1)).findAll();
    }

    @Test
    void findByDniUserTestOk()
    {
        Integer dni = getUser().getDni();
        when(userRepository.findByDni(dni)).thenReturn(Optional.of(getUser()));

        User user = userService.findByDni(dni);

        assertEquals(getUser(),user);
        verify(userRepository,times(1)).findByDni(dni);
    }

    @Test
    void findByDniTestFail()
    {
        when(userRepository.findByDni(12345654)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class,()->userService.findByDni(12345654));
    }

    @Test
    void getByUsernameAndPasswordTest()
    {
        String username = getUser().getUsername();
        String password = getUser().getPassword();
        when(userRepository.findByUsernameAndPassword(username,password)).thenReturn(getUser());

        User user = userService.getByUsernameAndPassword(username,password);

        assertEquals(getUser().getUsername(),user.getUsername());
    }
}

package com.utnphones.tputnphones.TestUtils;

import com.utnphones.tputnphones.domain.City;
import com.utnphones.tputnphones.domain.User;
import com.utnphones.tputnphones.dto.LoginRequestDto;
import com.utnphones.tputnphones.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

public class TestEntityFactory {

    public static User getUser() {
        return User.builder()
                .dni(41306781)
                .name("nicolas")
                .surname("roldan")
                .city(City.builder()
                        .name("Mar del plata")
                        .idCity(2L)
                        .prefixNumber(223)
                        .build())
                .province("buenos aires")
                .username("nicoRoldan")
                .password("nico123")
                .employee(true)
                .build();
    }

    public static List<User> getUserList(){
        List<User> userList = new ArrayList<>();
        userList.add(getUser());
        return userList;
    }

    public static UserDto getUserDto(){
        return UserDto.builder()
                .username("nicoRoldan")
                .employee(true)
                .build();
    }

    public static LoginRequestDto getLoginRequestDto(){
        return LoginRequestDto.builder()
                .username("nicoRoldan")
                .password("nico123")
                .build();
    }

    public static City getCity(){
        return City.builder()
                .idCity(1L)
                .prefixNumber(11)
                .name("Buenos Aires")
                .build();
    }

    public static List<City> getCityList(){
        List<City> cityList = new ArrayList<>();
        cityList.add(getCity());
        return cityList;
    }
}

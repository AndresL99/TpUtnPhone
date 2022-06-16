package com.utnphones.tputnphones.utils;

import com.utnphones.tputnphones.domain.Bill;
import com.utnphones.tputnphones.domain.Call;
import com.utnphones.tputnphones.domain.City;
import com.utnphones.tputnphones.domain.Client;
import com.utnphones.tputnphones.domain.PhoneLine;
import com.utnphones.tputnphones.domain.Tariff;
import com.utnphones.tputnphones.domain.User;
import com.utnphones.tputnphones.dto.CallDto;
import com.utnphones.tputnphones.dto.LoginRequestDto;
import com.utnphones.tputnphones.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestEntityFactory {

    public static City getCity() {
        return City.builder().idCity(1L).name("Buenos Aires").prefixNumber(11).build();
    }


    public static List<City> getCities() {
        List<City> cities = new ArrayList<>();
        cities.add(getCity());
        return cities;
    }

    public static User getUser() {
        return User.builder().dni(40444222)
                .city(getCity())
                .employee(true)
                .name("Andres")
                .surname("Lerner")
                .password("andres123")
                .province("Buenos Aires")
                .username("andreslerner")
                .build();
    }

    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(getUser());
        return users;
    }

    public static Tariff getTariff()
    {
        return Tariff.builder().
                idTariff(1L)
                .originCity(getCity())
                .destinationCity(getCity())
                .beginHour(new Time(10))
                .untilHour(new Time(10))
                .priceXMinute(40F)
                .build();
    }

    public static List<Tariff>getTariffs()
    {
        List<Tariff>tariffs = new ArrayList<>();
        tariffs.add(getTariff());
        return tariffs;
    }

    public static Client getClient()

    {
        return Client.builder().idClient(1L).user(getUser()).build();
    }

    public static List<Client>getClients()
    {
        List<Client>clients = new ArrayList<>();
        clients.add(getClient());
        return clients;
    }

    public static PhoneLine getTelephoneLine() {
        return PhoneLine.builder()
                .idClient(getClient())
                .phoneNumber("2241563256")
                .build();
    }

    public static List<PhoneLine>getPhoneLines()
    {
        List<PhoneLine>phoneLines = new ArrayList<>();
        phoneLines.add(getTelephoneLine());
        return phoneLines;
    }

    public static Bill getBill()
    {
        return Bill.builder()
                .idBill(1L)
                .idClient(getClient())
                .expirationDate(new Date())
                .invoiceDate(new Date())
                .numberCalls(2)
                .totalPrice(1000F)
                .build();
    }

    public static List<Bill>getBills()
    {
        List<Bill>bills = new ArrayList<>();
        bills.add(getBill());
        return bills;
    }

    public static Call getCall()
    {
        return Call.builder().idCall(1L).bill(getBill()).destinationCity(getCity()).originCity(getCity()).destinationNumber(getTelephoneLine())
                .duration(2L).tariff(getTariff()).totalPrice(1000F).originNumber(getTelephoneLine()).dayTime(new Timestamp(100)).build();
    }

    public static List<Call>getCalls()
    {
        List<Call>calls = new ArrayList<>();
        calls.add(getCall());
        return calls;
    }

    public static Pageable aPageable()
    {
        return PageRequest.of(0,10);
    }

    public static Page<Bill> getBillPage()
    {
        return new PageImpl<>(List.of(getBill()));
    }

    public static Page<Call>getCallPage()
    {
        return new PageImpl<>(List.of(getCall()));
    }

    public static UserDto getUserDto(){
        return UserDto.builder()
                .username("nicoRoldan")
                .employee(true)
                .build();
    }

    public static LoginRequestDto getLoginRequestDto(){
        return LoginRequestDto.builder()
                .username("andreslerner")
                .password("andres123")
                .build();
    }
    public static CallDto getCallDto(){
        return CallDto.builder()
                .origin("2235583444")
                .destination("1147475566")
                .datetime("06/02/2018 15:00:00")
                .duration(10L)
                .build();
    }

    public static UserDto aBackOffice()
    {
        return UserDto.builder().username("nicoRoldan").employee(true).build();
    }


}

package com.utnphones.tputnphones.TestUtils;

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

    public static Call getCall(){
        return Call.builder()
                .idCall(1L)
                .duration(10L)
                .totalPrice(10f)
                .originNumber(PhoneLine.builder()
                        .phoneNumber("2235583444")
                        .build())
                .destinationNumber(PhoneLine.builder()
                        .phoneNumber("1147475566")
                        .build())
                .originCity(City.builder()
                        .idCity(2L)
                        .build())
                .destinationCity(City.builder()
                        .idCity(1L)
                        .build())
                .tariff(Tariff.builder()
                        .idTariff(1L)
                        .build())
                .bill(Bill.builder()
                        .idBill(1L)
                        .build())
                .build();
    }

    public static List<Call> getCallList(){
        List<Call> callList = new ArrayList<>();
        callList.add(getCall());
        return callList;
    }

    public static CallDto getCallDto(){
        return CallDto.builder()
                .origin("2235583444")
                .destination("1147475566")
                .datetime("06/02/2018 15:00:00")
                .duration(10L)
                .build();
    }

    public static Bill getBill(){
        return Bill.builder()
                .idBill(1L)
                .numberCalls(1)
                .totalPrice(10f)
                //.invoiceDate("06/02/2018 15:00:00")
                //.expirationDate("06/02/2018 15:00:00")
                .idClient(Client.builder()
                        .idClient(1L)
                        .build())
                .build();
    }

    public static List<Bill> getBillList(){
        List<Bill> billList = new ArrayList<>();
        billList.add(getBill());
        return billList;
    }

    public static Client getClient(){
        return Client.builder()
                .idClient(2L)
                .user(User.builder()
                        .username("nicoRoldan")
                        .password("nico123")
                        .build())
                .build();
    }

    public static List<Client> getClientList(){
        List<Client> clientList = new ArrayList<>();
        clientList.add(getClient());
        return clientList;
    }

    public static Tariff getTariff(){
        return Tariff.builder()
                .idTariff(1L)
                .originCity(getCity())
                .destinationCity(getCity())
                .priceXMinute(1F)
                //.beginHour("12:00:00")
                //.untilHour()
                .build();
    }

    public static List<Tariff> getTariffList(){
        List<Tariff> tariffList = new ArrayList<>();
        tariffList.add(getTariff());
        return tariffList;
    }

    public static PhoneLine getPhoneLine(){
        return PhoneLine.builder()
                .phoneNumber("2235559562")
                .idClient(getClient())
                .build();
    }

    public static List<PhoneLine> getPhoneLineList() {
        List<PhoneLine> phoneLineList = new ArrayList<>();
        phoneLineList.add(getPhoneLine());
        return phoneLineList;
    }

    public static UserDto aBackOffice()
    {
        return UserDto.builder().username("nicoRoldan").employee(true).build();
    }

    public static Pageable getPageable()
    {
        return PageRequest.of(0,10);
    }

    public static Page<Call> getCallPage()
    {
        return new PageImpl<>(List.of(getCall()));
    }

    public static Page<Bill> getBillPage() {return new PageImpl<>(List.of(getBill()));}
}

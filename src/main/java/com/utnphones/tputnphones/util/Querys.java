package com.utnphones.tputnphones.util;

public class Querys {

    public static final String GET_CITY_ID_BY_PHONE_NUMBER = " select id_city from cities c\n" +
            " where c.prefix_number = (select c.prefix_number from cities c\n" +
            " where ?1 like concat(c.prefix_number,'%'));";

    public static final String GET_TARIFF_BY_NAMES = " select t.id_tariff from tariffs t \n" +
            "WHERE t.id_city_origin = ?1 \n" +
            "and t.id_city_destination = ?2";

    public static final String GET_TARIFF_BY_CITIES_ID = " select * from tariffs t \n" +
            "WHERE t.id_city_origin = ?1 \n" +
            "and t.id_city_destination = ?2";
}

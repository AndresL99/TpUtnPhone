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

    public static final String GET_CALLS_FOR_USER_EMPLOYEE = "SELECT c.* " +
            "FROM calls c " +
            "INNER JOIN telephone_Lines tl " +
            "ON tl.telephone_number = c.id_number_origin " +
            "INNER JOIN users u " +
            "WHERE u.dni = ?1 AND c.day_time BETWEEN ?2 AND ?3 ";

    public static final String GET_CALLS_FOR_USER_CLIENT = "SELECT c.* " +
            "FROM calls c " +
            "INNER JOIN telephone_Lines tl " +
            "ON tl.telephone_number = c.id_number_origin " +
            "INNER JOIN clients cl " +
            "WHERE cl.id_client = ?1 AND c.day_time BETWEEN ?2 AND ?3 ";

    public static final String GET_BILLS_FOR_USER_CLIENT = "SELECT b.* FROM bills b " +
            "JOIN clients c ON b.id_client = c.id_client " +
            "WHERE c.id_client = ?1 AND b.date_facturation BETWEEN ?2 AND ?3 ";

    public static final String GET_CLIENT_USERNAME = "Select c.* from clients c " +
            "join users u on c.dni = u.dni " +
            "where u.username = ?1 ";

    public static final String CALCULATE =
            " select t.id_tariff,t.id_city_origin,t.id_city_destination,t.prece_x_minute,t.begin_hour,t.until_hour " +
                    " from tariffs t " +
                    " left join cities co " +
                    " on t.id_city_origin= co.id_city " +
                    " left join cities cd " +
                    " on t.id_city_destination= cd.id_city " +
                    " where ?1 like concat(co.prefix_number,'%') " +
                    " and ?2 like concat(cd.prefix_number,'%') " +
                    " and length(co.prefix_number)= ( " +
                    " select max(length(co.prefix_number)) as lenght from tariffs t join cities co " +
                    " on co.id_city= t.id_city_origin " +
                    " where ?1 like concat(co.prefix_number,'%') " +
                    "			) " +
                    " and length(cd.prefix_number)= ( " +
                    " select max(length(cd.prefix_number)) as lenght from tariffs t join cities cd " +
                    " on cd.id_city= t.id_city_destination " +
                    " where ?2 like concat(cd.prefix_number,'%') " +
                    "        ); " ;
}

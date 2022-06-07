
drop database utn_phones;
create database Utn_Phones;
use Utn_Phones;


create table cities(
                       name varchar(30),
                       id_city bigint not null,
                       prefix_number integer not null,
                       constraint pk_id_city primary key (id_city)
);

create table users(
                      name varchar(30),
                      surname varchar(30),
                      dni integer not null,
                      id_city bigint not null,
                      province varchar(50) not null,
                      username varchar(50) not null,
                      is_employee bool default 0 not null,
                      password varchar(50) not null,
                      constraint pk_dni primary key (dni),
                      constraint fk_city foreign key(id_city) references cities(id_city)
);

create table clients(
                        id_client bigint not null,
                        dni integer not null,
                        constraint pk_id_client primary key (id_client),
                        constraint fk_dni_client foreign key(dni) references users(dni)
);

create table telephone_Lines(
                                telephone_number varchar(20) not null,
                                id_client bigint not null,
                                constraint pk_telephone_number primary key (telephone_number),
                                constraint fk_id_client foreign key(id_client) references clients(id_client));

create table tariffs(
                        id_tariff bigint not null,
                        id_city_origin bigint not null,
                        id_city_destination bigint not null,
                        prece_x_minute float not null,
                        begin_hour time not null,
                        until_hour time not null,
                        constraint pk_id_tariff primary key (id_tariff),
                        constraint fk_city_origin foreign key(id_city_origin) references cities(id_city),
                        constraint fk_city_destination foreign key(id_city_destination) references cities(id_city)
);



create table bills(
                      id_bill bigint not null,
                      id_client bigint not null,
                      number_calls integer not null,
                      price_total float not null,
                      date_facturation date not null,
                      date_expiration date not null,
                      constraint pk_id_bill primary key (id_bill),
                      constraint fk_id_client_bill foreign key(id_client) references clients(id_client));

create table calls(
                      id_call bigint auto_increment,
                      id_number_origin varchar(20) ,
                      id_number_destination varchar(20) ,
                      id_city_origin bigint ,
                      id_city_destination bigint ,
                      id_tariff bigint ,
                      day_time datetime ,
                      duration bigint ,
                      price_total float ,
                      id_bill bigint ,
                      constraint pk_id_call primary key (id_call),
                      constraint fk_number_origin foreign key(id_number_origin) references telephone_Lines(telephone_number),
                      constraint fk_number_destination foreign key(id_number_destination) references telephone_Lines(telephone_number),
                      constraint fk_city_origin_call foreign key(id_city_origin) references cities(id_city),
                      constraint fk_city_destination_call foreign key(id_city_destination) references cities(id_city),
                      constraint fk_tariff foreign key(id_tariff) references tariffs(id_tariff),
                      constraint fk_bill foreign key(id_bill) references bills(id_bill)
);

package com.utnphones.tputnphones.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Time;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tariffs")
public class Tariff
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tariff")
    private Long idTariff;

    @OneToOne
    @JoinColumn(name = "id_city_origin")
    private City originCity;

    @OneToOne
    @JoinColumn(name = "id_city_destination")
    private City destinationCity;

    @Column(name = "prece_x_minute")
    private Float priceXMinute;

    @Column(name = "begin_hour")
    private Time beginHour;

    @Column(name = "until_hour")
    private Time untilHour;
}

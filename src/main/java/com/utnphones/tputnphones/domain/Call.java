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
@Table(name = "calls")
public class Call {
    @Id
    @Column(name = "id_call")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCall;

    @Column(name = "duration")
    private Time duration;

    @Column(name = "price_total")
    private Float totalPrice;

    @OneToOne
    @JoinColumn(name = "id_number_origin")
    private phoneLine originNumber;

    @OneToOne
    @JoinColumn(name = "id_number_destination")
    private phoneLine destinationNumber;

    @OneToOne
    @JoinColumn(name = "id_city_origin")
    private City originCity;

    @OneToOne
    @JoinColumn(name = "id_city_destination")
    private City destinationCity;

    @OneToOne
    @JoinColumn(name = "id_tariff")
    private Tariff tariff;

    @OneToOne
    @JoinColumn(name = "id_bill")
    private Bill bill;


}

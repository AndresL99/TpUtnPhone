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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "bills")
public class Bill
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bill")
    private Long idBill;

    @Column(name = "number_calls")
    private Integer numberCalls;

    @Column(name = "price_total")
    private Float totalPrice;

    @Column(name = "date_facturation")
    private Date invoiceDate;

    @Column(name = "date_expiration")
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_dni")
    private User userDni;

}

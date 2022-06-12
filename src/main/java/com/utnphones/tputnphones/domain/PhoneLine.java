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

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "telephone_Lines")
public class PhoneLine
{
    @Id
    @Column(name = "telephone_number")
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "id_client")
    private Client idClient;
}

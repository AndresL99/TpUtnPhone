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
@Table(name = "users")
public class User
{
    @Id
    @Column(name = "dni")
    private Integer dni;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @OneToOne
    @JoinColumn(name = "id_city")
    private City city;

    @Column(name = "province")
    private String province;
}

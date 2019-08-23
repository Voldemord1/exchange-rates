package com.exchange.rates.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "currency", schema = "exchange_rates")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mnemonics", length = 10, unique = true, nullable = false)
    private String mnemonics;

    @Column(name = "code", length = 10, unique = true, nullable = false)
    private String code;

    @Column(name = "description")
    private String description;

    public Currency() {
    }

    public Currency(String mnemonics, String code, String description) {
        this.mnemonics = mnemonics;
        this.code = code;
        this.description = description;
    }
}

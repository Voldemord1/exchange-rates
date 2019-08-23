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
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "journal", schema = "exchange_rates")
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date", nullable = false)
    private Long date;

    @Column(name = "code_a")
    private String currencyCodeA;

    @Column(name = "code_b")
    private String currencyCodeB;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @Column(name = "rateBuy")
    private Double rateBuy;

    @Column(name = "rateSell")
    private Double rateSell;

    public Journal() {
    }

    public Journal(Long date, String currencyCodeA, String currencyCodeB,
                   Currency currency, Double rateBuy, Double rateSell) {
        this.date = date;
        this.currencyCodeA = currencyCodeA;
        this.currencyCodeB = currencyCodeB;
        this.currency = currency;
        this.rateBuy = rateBuy;
        this.rateSell = rateSell;
    }
}

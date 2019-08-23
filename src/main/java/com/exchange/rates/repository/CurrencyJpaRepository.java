package com.exchange.rates.repository;

import com.exchange.rates.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyJpaRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> getCurrencyByMnemonics(String mnemonics);
    Optional<Currency> getCurrencyByCode(String code);
}

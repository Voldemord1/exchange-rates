package com.exchange.rates.repository;

import com.exchange.rates.model.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JournalJpaRepository extends JpaRepository<Journal, Long> {

    Optional<Journal> findByCurrencyCodeA(String code);
}

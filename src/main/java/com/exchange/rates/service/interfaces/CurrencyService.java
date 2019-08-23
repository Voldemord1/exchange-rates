package com.exchange.rates.service.interfaces;

import com.exchange.rates.model.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyService {

    void addCurrency(Currency currency);
    List<Currency> getAllCurrencies();
    Optional<Currency> getCurrencyByMnemonics(String mnemonics);
    Optional<Currency> getCurrencyByCode(String code);
}

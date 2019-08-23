package com.exchange.rates.service.impl;

import com.exchange.rates.model.Currency;
import com.exchange.rates.repository.CurrencyJpaRepository;
import com.exchange.rates.service.interfaces.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final CurrencyJpaRepository currencyJpaRepository;

    @Autowired
    public CurrencyServiceImpl(CurrencyJpaRepository currencyJpaRepository) {
        this.currencyJpaRepository = currencyJpaRepository;
    }

    @Override
    @Transactional
    public void addCurrency(Currency currency) {
        currencyJpaRepository.save(currency);
        logger.info(currency + " saved in DB");
    }

    @Override
    @Transactional(readOnly = true)
    public List<Currency> getAllCurrencies() {
        return currencyJpaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Currency> getCurrencyByMnemonics(String mnemonics) {
        return currencyJpaRepository.getCurrencyByMnemonics(mnemonics);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Currency> getCurrencyByCode(String code) {
        return currencyJpaRepository.getCurrencyByCode(code);
    }
}

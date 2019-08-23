package com.exchange.rates.service.impl;

import com.exchange.rates.model.Currency;
import com.exchange.rates.model.Journal;
import com.exchange.rates.repository.CurrencyJpaRepository;
import com.exchange.rates.repository.JournalJpaRepository;
import com.exchange.rates.service.interfaces.JournalService;
import com.exchange.rates.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class JournalServiceImpl implements JournalService {

    private final JournalJpaRepository journalJpaRepository;
    private final CurrencyJpaRepository currencyJpaRepository;
    private final Logger logger = LoggerFactory.getLogger(JournalServiceImpl.class);

    @Autowired
    public JournalServiceImpl(JournalJpaRepository journalJpaRepository,
                              CurrencyJpaRepository currencyJpaRepository) {
        this.journalJpaRepository = journalJpaRepository;
        this.currencyJpaRepository = currencyJpaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Journal> getJournalByCodeAndDate(String code, Long date) {
        Optional<Journal> journal = journalJpaRepository.findByCurrencyCodeA(code);
        if (journal.isPresent()) {
            String timeFromJournal = DateUtil.convertToStringDate(journal.get().getDate());
            String currentTime = DateUtil.convertToStringDate(date / 1000);
            if (currentTime.equals(timeFromJournal)) {
                return journal;
            }
        } else {
            logger.warn("No such journal with code " + code);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void saveJournal(Journal journal) {
        journalJpaRepository.save(journal);
        logger.info(journal + " save in DB");
    }

    @Override
    @Transactional
    public void saveOrUpdate(Journal journal) {
        if (Objects.nonNull(journal.getCurrencyCodeA())) {
            Optional<Journal> journalByCode =
                    journalJpaRepository.findByCurrencyCodeA(journal.getCurrencyCodeA());
            if (journalByCode.isPresent()) {
                journalByCode.get().setDate(journal.getDate());
                journalByCode.get().setCurrencyCodeA(journal.getCurrencyCodeA());
                journalByCode.get().setCurrencyCodeB(journal.getCurrencyCodeB());
                journalByCode.get().setCurrency(journal.getCurrency());
                journalByCode.get().setRateBuy(journal.getRateBuy());
                journalByCode.get().setRateSell(journal.getRateSell());
                logger.info("Journal ID: " + journalByCode.get().getId() + " updated in DB");
            } else {
                journalJpaRepository.save(journal);
                logger.info(journal + " save in DB");
            }
        }
    }

    @Override
    public Optional<Journal> getJournalByCode(String code) {
        return journalJpaRepository.findByCurrencyCodeA(code);
    }

    @Override
    @Transactional
    public void setNewJournals(Journal[] journals) {
        Optional<Currency> currencyUAH = currencyJpaRepository.getCurrencyByMnemonics("UAH");
        for (Journal journal : journals) {
            String code = journal.getCurrencyCodeA();
            Optional<Currency> currencyByCode = currencyJpaRepository.getCurrencyByCode(code);
            if (currencyByCode.isPresent() && currencyUAH.isPresent()) {
                if (journal.getCurrencyCodeB().equals(currencyUAH.get().getCode())) {
                    Journal newJournal = new Journal(
                            journal.getDate(),
                            journal.getCurrencyCodeA(),
                            journal.getCurrencyCodeB(),
                            currencyByCode.get(),
                            journal.getRateBuy(),
                            journal.getRateSell());
                    saveOrUpdate(newJournal);
                }
            }
        }
    }
}

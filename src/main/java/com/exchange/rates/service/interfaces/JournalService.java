package com.exchange.rates.service.interfaces;

import com.exchange.rates.model.Journal;

import java.util.Optional;

public interface JournalService {

    Optional<Journal> getJournalByCodeAndDate(String code, Long date);
    Optional<Journal> getJournalByCode(String code);
    void saveJournal(Journal journal);
    void saveOrUpdate(Journal journal);
    void setNewJournals(Journal[] journals);

}

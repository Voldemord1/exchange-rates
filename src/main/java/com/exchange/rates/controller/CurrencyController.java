package com.exchange.rates.controller;

import com.exchange.rates.model.Currency;
import com.exchange.rates.model.Journal;
import com.exchange.rates.service.interfaces.CurrencyService;
import com.exchange.rates.service.interfaces.JournalService;
import com.exchange.rates.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class CurrencyController {

    private final CurrencyService currencyService;
    private final JournalService journalService;
    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(CurrencyController.class);
    private static final String URL_API = "https://api.monobank.ua/bank/currency";

    @Autowired
    public CurrencyController(CurrencyService currencyService, JournalService journalService,
                              RestTemplate restTemplate) {
        this.currencyService = currencyService;
        this.journalService = journalService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/currency")
    public String selectCurrency(Model model, @ModelAttribute("message") String message) {
        List<Currency> currencies = currencyService.getAllCurrencies();
        model.addAttribute("currencies", currencies);
        model.addAttribute("message", message);
        return "currency";
    }

    @PostMapping("/currency")
    public String currency(Model model, @RequestParam("mnemonics") String mnemonics) {
        Optional<Currency> currencyByMnemonics = currencyService.getCurrencyByMnemonics(mnemonics);
        if (currencyByMnemonics.isPresent()) {
            while (true) {
                Optional<Journal> journalByCode
                        = journalService.getJournalByCode(currencyByMnemonics.get().getCode());
                if (!journalByCode.isPresent()) {
                    model.addAttribute("message", "Error. Choose another currency, please.");
                    break;
                }
                Optional<Journal> journalByCodeAndDate = journalService.getJournalByCodeAndDate(
                        currencyByMnemonics.get().getCode(), System.currentTimeMillis());
                if (journalByCodeAndDate.isPresent()) {
                    String date = DateUtil.convertToStringDate(journalByCodeAndDate.get().getDate());
                    model.addAttribute("date", date);
                    model.addAttribute("currency", currencyByMnemonics.get());
                    model.addAttribute("journal", journalByCodeAndDate.get());
                    break;
                } else {
                    Journal[] journals = restTemplate.getForObject(URL_API, Journal[].class);
                    if (Objects.nonNull(journals)) {
                        journalService.setNewJournals(journals);
                    } else {
                        logger.warn("Doesn't get JSON from " + URL_API);
                    }
                }
            }
            return "currency_rates";
        }
        model.addAttribute("message", "Error. Such currency not exist in Dictionary.");
        return "redirect:/admin/currency";
    }
}

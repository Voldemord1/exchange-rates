package com.exchange.rates.controller;

import com.exchange.rates.model.Currency;
import com.exchange.rates.model.Journal;
import com.exchange.rates.model.User;
import com.exchange.rates.service.interfaces.CurrencyService;
import com.exchange.rates.service.interfaces.JournalService;
import com.exchange.rates.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Controller
public class InitController {

    private final UserService userService;
    private final CurrencyService currencyService;
    private final JournalService journalService;
    private final RestTemplate restTemplate;
    private static final String URL_API = "https://api.monobank.ua/bank/currency";

    @Autowired
    public InitController(UserService userService, CurrencyService currencyService,
                          JournalService journalService, RestTemplate restTemplate) {
        this.userService = userService;
        this.currencyService = currencyService;
        this.journalService = journalService;
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    private void init() {
        User admin = new User("admin@gmail.com", "123", "ROLE_ADMIN");
        User user = new User("user@gmail.com", "123", "ROLE_USER");
        userService.addUser(admin);
        userService.addUser(user);

        Currency usd = new Currency("USD", "840", "Dollar USA");
        Currency euro = new Currency("EUR", "978", "Euro");
        Currency hryvnia = new Currency("UAH", "980", "Ukrainian hryvnia");
        currencyService.addCurrency(usd);
        currencyService.addCurrency(euro);
        currencyService.addCurrency(hryvnia);

        Journal[] journals = restTemplate.getForObject(URL_API, Journal[].class);
        if (Objects.nonNull(journals)) {
            journalService.setNewJournals(journals);
        }
    }
}

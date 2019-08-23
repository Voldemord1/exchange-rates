package com.exchange.rates.service.interfaces;

import com.exchange.rates.model.User;

import java.util.Optional;

public interface UserService {

    void addUser(User user);
    Optional<User> getUserByEmail(String email);
}

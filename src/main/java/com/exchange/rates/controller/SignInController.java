package com.exchange.rates.controller;

import com.exchange.rates.model.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
public class SignInController {

    @GetMapping(path = "/")
    public String login(@AuthenticationPrincipal User user) {
        if (Objects.nonNull(user) && user.getRole().equals("ROLE_ADMIN")) {
            return "redirect:/admin/currency";
        } else {
            return "redirect:/login";
        }
    }
}

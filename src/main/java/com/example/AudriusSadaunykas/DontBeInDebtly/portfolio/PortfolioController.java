package com.example.AudriusSadaunykas.DontBeInDebtly.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/{year}:{month}")
    public List<PortfolioItem> getUserPortfolio(@PathVariable int year, @PathVariable int month,
                                                @AuthenticationPrincipal Object user) {
        return portfolioService.showPortfolio(Long.valueOf(user.toString()), year, month);
    }
}

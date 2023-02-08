package com.example.AudriusSadaunykas.DontBeInDebtly.portfolio;

import com.example.AudriusSadaunykas.DontBeInDebtly.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/{year}:{month}")
    public List<PortfolioItem> getUserPortfolio(@PathVariable int year, @PathVariable int month,
                                                @AuthenticationPrincipal UserPrincipal user) {
        return portfolioService.showPortfolio(user.getUserId(), year, month);
    }
}

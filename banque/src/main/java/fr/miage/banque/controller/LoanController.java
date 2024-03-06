package fr.miage.banque.controller;

import fr.miage.banque.domain.entity.Loan;
import fr.miage.banque.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/loans")
@AllArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @PostMapping("/create")
    public Loan createLoan(@RequestBody Loan loan) {
        return loanService.createLoan(loan);
    }

     @GetMapping
    public List<Loan> getLoans() {
        return loanService.getLoans();
    }

    @PutMapping("/update/{id}")
    public Loan modifyLoan(@PathVariable("id") UUID id, @RequestBody Loan loan) {
        return loanService.modifyLoan(id, loan);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteLoan(@PathVariable("id") UUID id) {
        return loanService.deleteLoan(id);
    }
}

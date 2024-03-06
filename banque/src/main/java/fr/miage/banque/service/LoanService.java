package fr.miage.banque.service;

import fr.miage.banque.domain.entity.Loan;

import java.util.List;

public interface LoanService {
    Loan createLoan(Loan loan);

    List<Loan> getLoans();

    Loan modifyLoan(Long id, Loan loan);

    String deleteLoan(Long id);
}

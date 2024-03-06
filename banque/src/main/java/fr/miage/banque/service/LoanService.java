package fr.miage.banque.service;

import fr.miage.banque.domain.entity.Loan;

import java.util.List;
import java.util.UUID;

public interface LoanService {
    Loan createLoan(Loan loan);

    List<Loan> getLoans();

    Loan modifyLoan(UUID id, Loan loan);

    String deleteLoan(UUID id);
}

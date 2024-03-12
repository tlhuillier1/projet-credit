package fr.miage.banque.service;

import fr.miage.banque.domain.entity.Loan;
import fr.miage.banque.domain.entity.Worker;

import java.util.List;

public interface LoanService {
    Loan createLoan(Loan loan);

    List<Loan> getLoans(String status);

    Loan getLoan(Long id);

    Loan modifyLoan(Long id, Loan loan);

    String deleteLoan(Long id);

    String getStatus(Long id);

    Loan applyForLoan(Long id);

    Loan reviewLoan(Long id, String decision, Worker advisor);
}

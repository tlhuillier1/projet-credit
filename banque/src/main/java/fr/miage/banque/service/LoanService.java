package fr.miage.banque.service;

import fr.miage.banque.domain.entity.Advisor;
import fr.miage.banque.domain.entity.Loan;

import java.util.List;

public interface LoanService {
    Loan createLoan(Loan loan);

    List<Loan> getLoans();

    Loan getLoan(Long id);

    Loan modifyLoan(Long id, Loan loan);

    String deleteLoan(Long id);

    String getStatus(Long id);

    Loan applyForLoan(Long id);

    List<Loan> getLoansInStudy();

    Loan reviewLoan(Long id, String decision, Advisor advisor);
}

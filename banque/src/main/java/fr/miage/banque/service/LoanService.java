package fr.miage.banque.service;

import fr.miage.banque.domain.entity.LoanApplication;
import fr.miage.banque.domain.entity.Worker;

import java.util.List;

public interface LoanService {
    LoanApplication createLoan(LoanApplication loanApplication);

    List<LoanApplication> getLoans(String status);

    LoanApplication getLoan(Long id);

    LoanApplication modifyLoan(Long id, LoanApplication loanApplication);

    String deleteLoan(Long id);

    String getStatus(Long id);

    LoanApplication applyForLoan(Long id);

    LoanApplication reviewLoan(Long id, String decision, Worker advisor);

    LoanApplication validateLoan(Long id, Worker worker, String decision);
}

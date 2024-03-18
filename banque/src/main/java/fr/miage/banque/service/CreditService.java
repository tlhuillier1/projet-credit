package fr.miage.banque.service;

import fr.miage.banque.domain.entity.Credit;
import fr.miage.banque.domain.entity.LoanApplication;

import java.util.List;

public interface CreditService {
    Credit createCredit(LoanApplication loanApplication);

    List<Credit> getCredits();

    Credit getCredit(Long id);
}

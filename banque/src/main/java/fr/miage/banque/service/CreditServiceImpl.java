package fr.miage.banque.service;

import fr.miage.banque.domain.entity.Credit;
import fr.miage.banque.domain.entity.LoanApplication;
import fr.miage.banque.repository.CreditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class CreditServiceImpl implements CreditService {

    CreditRepository creditRepository;

    @Override
    public Credit createCredit(LoanApplication loanApplication) {
        Credit credit = new Credit();
        credit.setAcceptationDate(LocalDate.now());
        credit.setLoanApplication(loanApplication);
        double due = (loanApplication.getAmount() * (1 + loanApplication.getRate() / 100))/loanApplication.getDuration();
        credit.setDue(due);
        return creditRepository.save(credit);
    }

    @Override
    public List<Credit> getCredits() {
        return creditRepository.findAll();
    }

    @Override
    public Credit getCredit(Long id) {
        return creditRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Credit not found"));
    }
}

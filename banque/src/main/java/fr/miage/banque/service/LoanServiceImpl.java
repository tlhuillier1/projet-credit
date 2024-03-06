package fr.miage.banque.service;

import fr.miage.banque.domain.entity.Loan;
import fr.miage.banque.domain.entity.LoanStatus;
import fr.miage.banque.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @Override
    public Loan createLoan(Loan loan) {
        loan.setCreatedAt(LocalDate.now());
        loan.setUpdatedAt(LocalDateTime.now());
        loan.setStatus(LoanStatus.DEBUT);
        return loanRepository.save(loan);
    }

    @Override
    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan modifyLoan(Long id, Loan loan) {
        return loanRepository.findById(id)
                .map(l -> {
                    l.setAmount(loan.getAmount());
                    l.setUpdatedAt(LocalDateTime.now());
                    l.setDuration(loan.getDuration());
                    return loanRepository.save(l);
                })
                .orElseThrow(() -> new RuntimeException("Loan not found"));
    }

    @Override
    public String deleteLoan(Long id) {
        loanRepository.deleteById(id);
        return "Loan deleted";
    }

    @Override
    public String getStatus(Long id) {
        return loanRepository.findById(id)
                .map(loan -> loan.getStatus().toString())
                .orElseThrow(() -> new RuntimeException("Loan not found"));
    }

}

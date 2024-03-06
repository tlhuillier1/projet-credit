package fr.miage.banque.service;

import fr.miage.banque.domain.entity.Loan;
import fr.miage.banque.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @Override
    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan modifyLoan(UUID id, Loan loan) {
        return loanRepository.findById(id)
                .map(l -> {
                    l.setAmount(loan.getAmount());
                    l.setDate(loan.getDate());
                    l.setDuration(loan.getDuration());
                    return loanRepository.save(l);
                })
                .orElseThrow(() -> new RuntimeException("Loan not found"));
    }

    @Override
    public String deleteLoan(UUID id) {
        loanRepository.deleteById(id);
        return "Loan deleted";
    }

}

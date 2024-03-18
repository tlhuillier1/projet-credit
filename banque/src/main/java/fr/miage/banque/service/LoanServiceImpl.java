package fr.miage.banque.service;

import fr.miage.banque.domain.entity.BankJob;
import fr.miage.banque.domain.entity.LoanApplication;
import fr.miage.banque.domain.entity.LoanStatus;
import fr.miage.banque.domain.entity.Worker;
import fr.miage.banque.repository.LoanRepository;
import fr.miage.banque.repository.WorkerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final WorkerRepository workerRepository;

    @Override
    public LoanApplication createLoan(LoanApplication loanApplication) {
        loanApplication.setCreatedAt(LocalDate.now());
        loanApplication.setUpdatedAt(LocalDateTime.now());
        loanApplication.setStatus(LoanStatus.DEBUT);
        Worker worker = new Worker();
        worker.setFirstName("John");
        worker.setLastName("Doe");
        worker.setJob(BankJob.ADVISOR);
        workerRepository.save(worker);
        return loanRepository.save(loanApplication);
    }

    @Override
    public List<LoanApplication> getLoans(String status) {
        if (status != null) {
            return loanRepository.findByStatus(LoanStatus.valueOf(status));
        } else {
            return loanRepository.findAll();
        }
    }

    @Override
    public LoanApplication getLoan(Long id) {
        return loanRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Loan not found"));
    }

    @Override
    public LoanApplication modifyLoan(Long id, LoanApplication loanApplication) {
        return loanRepository.findById(id)
                .map(l -> {
                    l.setAmount(loanApplication.getAmount());
                    l.setUpdatedAt(LocalDateTime.now());
                    l.setDuration(loanApplication.getDuration());
                    return loanRepository.save(l);
                })
                .orElseThrow(() -> new NoSuchElementException("Loan not found"));
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
                .orElseThrow(() -> new NoSuchElementException("Loan not found"));
    }

    @Override
    public LoanApplication applyForLoan(Long id) {
        return loanRepository.findById(id)
                .map(loan -> {
                    loan.setStatus(LoanStatus.ETUDE);
                    return loanRepository.save(loan);
                })
                .orElseThrow(() -> new NoSuchElementException("Loan not found"));
    }

    @Override
    public LoanApplication reviewLoan(Long id, String decision, Worker advisor) {
        LoanStatus loanStatus;
        if (decision.equals("valide")) {
            loanStatus = LoanStatus.VALIDATION;
        } else if (decision.equals("refuse")) {
            loanStatus = LoanStatus.REJET;
        } else {
            throw new IllegalArgumentException("Invalid decision");
        }
        return loanRepository.findById(id)
                .map(loan -> {
                    loan.setStatus(loanStatus);
                    loan.setReviewedBy(advisor);
                    return loanRepository.save(loan);
                })
                .orElseThrow(() -> new NoSuchElementException("Loan not found"));
    }

    @Override
    public LoanApplication validateLoan(Long id, Worker worker, String decision) {
        LoanStatus loanStatus;
        if (decision.equals("valide")) {
            loanStatus = LoanStatus.VALIDATION;
        } else if (decision.equals("refuse")) {
            loanStatus = LoanStatus.REJET;
        } else {
            throw new IllegalArgumentException("Invalid decision");
        }
        return loanRepository.findById(id)
                .map(loan -> {
                    loan.setStatus(loanStatus);
                    loan.setValidateBy(worker);
                    return loanRepository.save(loan);
                })
                .orElseThrow(() -> new NoSuchElementException("Loan not found"));
    }
}

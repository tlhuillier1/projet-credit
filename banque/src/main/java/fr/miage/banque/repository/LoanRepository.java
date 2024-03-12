package fr.miage.banque.repository;

import fr.miage.banque.domain.entity.Loan;
import fr.miage.banque.domain.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByStatus(LoanStatus loanStatus);
}

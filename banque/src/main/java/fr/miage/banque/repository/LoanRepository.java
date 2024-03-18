package fr.miage.banque.repository;

import fr.miage.banque.domain.entity.LoanApplication;
import fr.miage.banque.domain.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanApplication, Long> {

    List<LoanApplication> findByStatus(LoanStatus loanStatus);
}

package fr.miage.banque.repository;

import fr.miage.banque.domain.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {

}

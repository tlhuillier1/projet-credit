package fr.miage.banque.repository;

import fr.miage.banque.domain.entity.Event;
import fr.miage.banque.domain.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByLoanApplication(LoanApplication loanApplication);
}

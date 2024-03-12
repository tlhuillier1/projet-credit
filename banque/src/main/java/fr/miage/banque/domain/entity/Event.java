package fr.miage.banque.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Event {

    public Event(Loan loan) {
        this.loan = loan;
        this.date = LocalDateTime.now();
        this.status = loan.getStatus();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loanId")
    private Loan loan;

    private LocalDateTime date;

    private LoanStatus status;
}

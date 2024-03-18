package fr.miage.banque.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private LoanApplication loanApplication;

    private LocalDateTime date;

    private LoanStatus status;

    public Event(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
        this.date = LocalDateTime.now();
        this.status = loanApplication.getStatus();
    }

    public Event() {
    }
}

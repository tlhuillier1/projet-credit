package fr.miage.banque.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Event {

    public Event(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
        this.date = LocalDateTime.now();
        this.status = loanApplication.getStatus();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loanId")
    private LoanApplication loanApplication;

    private LocalDateTime date;

    private LoanStatus status;
}

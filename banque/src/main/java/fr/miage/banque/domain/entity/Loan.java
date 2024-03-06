package fr.miage.banque.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public LocalDate createdAt;

    public LocalDateTime updatedAt;

    public double amount;

    public int duration;

    public LoanStatus status;

    @ManyToOne
    @JoinColumn(name = "clientId")
    public Client client;
}

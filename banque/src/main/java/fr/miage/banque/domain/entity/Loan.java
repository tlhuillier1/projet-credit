package fr.miage.banque.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class Loan {
    @Id
    @GeneratedValue
    public UUID id;

    public LocalDate date;

    public double amount;

    public int duration;
}

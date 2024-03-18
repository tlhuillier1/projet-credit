package fr.miage.banque.domain.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class LoanApplication extends RepresentationModel<LoanApplication> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public LocalDate createdAt;

    public LocalDateTime updatedAt;

    public double amount;

    public int duration;

    private double rate;

    public LoanStatus status;

    @OneToOne
    @JoinColumn(name = "reviewedBy")
    public Worker reviewedBy;

    @OneToOne
    @JoinColumn(name = "validateBy")
    public Worker validateBy;

    @ManyToOne
    @JoinColumn(name = "clientId")
    public Client client;
}
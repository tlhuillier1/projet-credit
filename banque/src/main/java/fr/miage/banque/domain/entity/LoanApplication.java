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
    private Long id;

    private LocalDate createdAt;

    private LocalDateTime updatedAt;

    private double amount;

    private int duration;

    private double rate;

    private LoanStatus status;

    @OneToOne
    @JoinColumn(name = "reviewedBy")
    private Worker reviewedBy;

    @OneToOne
    @JoinColumn(name = "validateBy")
    private Worker validateBy;

    @ManyToOne
    @JoinColumn(name = "clientId")
    private Client client;
}

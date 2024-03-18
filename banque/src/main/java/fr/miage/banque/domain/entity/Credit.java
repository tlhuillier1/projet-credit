package fr.miage.banque.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Entity
@Data
public class Credit extends RepresentationModel<Credit> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double due;

    @Column(name = "acceptation_date")
    private LocalDate acceptationDate;

    @OneToOne
    @JoinColumn(name = "loan_application_id")
    private LoanApplication loanApplication;

}

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

    private LocalDate acceptationDate;

    @OneToOne
    @JoinColumn(name = "loanApplicationId")
    private LoanApplication loanApplication;

}

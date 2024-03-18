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
@Table(name = "loan_application")
public class LoanApplication extends RepresentationModel<LoanApplication> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private double amount;

    private int duration;

    private double rate;

    private LoanStatus status;

    @OneToOne
    @JoinColumn(name = "reviewed_by")
    private Worker reviewedBy;

    @OneToOne
    @JoinColumn(name = "validate_by")
    private Worker validateBy;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
